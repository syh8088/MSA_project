package com.order_service.adapter.out.persistence.service;

import com.common.WebAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order_service.adapter.out.persistence.entity.OutBox;
import com.order_service.adapter.out.persistence.entity.PaymentEvent;
import com.order_service.adapter.out.persistence.entity.PaymentOrderHistory;
import com.order_service.adapter.out.persistence.repository.OutBoxRepository;
import com.order_service.adapter.out.persistence.repository.PaymentEventRepository;
import com.order_service.adapter.out.persistence.repository.PaymentOrderHistoryRepository;
import com.order_service.adapter.out.persistence.repository.PaymentOrderRepository;
import com.order_service.adapter.out.stream.util.PartitionKeyUtil;
import com.order_service.application.port.out.GetOrderPort;
import com.order_service.application.port.out.PaymentCheckOutPort;
import com.order_service.application.port.out.PaymentOrderStatusOutPut;
import com.order_service.application.port.out.PaymentStatusUpdatePort;
import com.order_service.domain.OutBoxStatus;
import com.order_service.domain.PaymentExecutionResultOutPut;
import com.order_service.domain.PaymentOrderStatus;
import com.order_service.domain.PaymentOrderWithSellerOutPut;
import com.order_service.domain.message.PaymentEventMessage;
import com.order_service.domain.message.PaymentEventMessageType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class PaymentOrderPersistenceAdapter implements PaymentCheckOutPort, PaymentStatusUpdatePort, GetOrderPort {

    private final PaymentEventRepository paymentEventRepository;
    private final PaymentOrderRepository paymentOrderRepository;
    private final PaymentOrderHistoryRepository paymentOrderHistoryRepository;
    private final OutBoxRepository outBoxRepository;

    private final PartitionKeyUtil partitionKeyUtil;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void insertPaymentCheckOut(PaymentEvent paymentEvent) {
        paymentEventRepository.save(paymentEvent);
        paymentOrderRepository.saveAll(paymentEvent.getPaymentOrderList());
    }

    @Override
    public void updatePaymentStatusToExecuting(String orderId, String paymentKey) {

    }

    @Transactional
    @Override
    public void updatePaymentStatus(PaymentExecutionResultOutPut paymentExecutionResult) {

        PaymentOrderStatus paymentStatus = paymentExecutionResult.getPaymentStatus();

        switch (paymentStatus) {
            case SUCCESS:
                this.updatePaymentStatusToSuccess(paymentExecutionResult);
                break;
            case FAILURE, UNKNOWN:
                this.updatePaymentStatusToFailureOrUnknown(paymentExecutionResult);
                break;
            default: {
                // ERROR Exception
                log.error("!오류! 결제 상태 업그레이드 에러 발생 PaymentStatusUpdateApiService#updatePaymentStatus getOrderId() = {}", paymentExecutionResult.getOrderId());
                throw new IllegalArgumentException("결제 상태 업그레이드 에러 발생 ## 주문 아이디값: " + paymentExecutionResult.getOrderId());
            }
        }
    }

    private void updatePaymentStatusToSuccess(PaymentExecutionResultOutPut paymentExecutionResult) {

        List<PaymentOrderStatusOutPut> paymentOrderStatusList
                = this.selectPaymentOrderStatusListByOrderId(paymentExecutionResult.getOrderId());
        this.insertPaymentOrderHistoryList(paymentOrderStatusList, paymentExecutionResult.getPaymentStatus(), "PAYMENT_CONFIRMATION_DONE");
        this.updatePaymentOrderStatusByOrderId(paymentExecutionResult.getOrderId(), paymentExecutionResult.getPaymentStatus());
        this.updatePaymentEventExtraDetails(
                paymentExecutionResult.getOrderId(),
                paymentExecutionResult.getPaymentKey(),
                paymentExecutionResult.getExtraDetails().getApprovedAt(),
                true
        );

        String orderId = paymentExecutionResult.getOrderId();
        int partitionKey = partitionKeyUtil.createPartitionKey(orderId.hashCode());
        PaymentEventMessage paymentEventMessage = this.createPaymentEventMessage(orderId, partitionKey);
        this.insertOutBox(paymentEventMessage);

        eventPublisher.publishEvent(paymentEventMessage);
    }

    private void updatePaymentStatusToFailureOrUnknown(PaymentExecutionResultOutPut paymentExecutionResult) {

        List<PaymentOrderStatusOutPut> paymentOrderStatusList
                = this.selectPaymentOrderStatusListByOrderId(paymentExecutionResult.getOrderId());
        this.insertPaymentOrderHistoryList(paymentOrderStatusList, paymentExecutionResult.getPaymentStatus(), "PAYMENT_CONFIRMATION_DONE");
        this.updatePaymentOrderStatusByOrderId(paymentExecutionResult.getOrderId(), paymentExecutionResult.getPaymentStatus());
        this.updatePaymentEventExtraDetails(
                paymentExecutionResult.getOrderId(),
                paymentExecutionResult.getPaymentKey(),
                null,
                false
        );
    }

    private PaymentEventMessage createPaymentEventMessage(String orderId, int partitionKey) {
        return PaymentEventMessage.of(
                PaymentEventMessageType.PAYMENT_CONFIRMATION_SUCCESS,
                Map.of("orderId", orderId),
                Map.of("partitionKey", String.valueOf(partitionKey))
        );
    }

    private List<PaymentOrderStatusOutPut> selectPaymentOrderStatusListByOrderId(String orderId) {
        return paymentOrderRepository.selectPaymentOrderStatusListByOrderId(orderId);
    }


    private BigDecimal selectTotalAmountByOrderId(String orderId) {
        return paymentOrderRepository.selectTotalAmountByOrderId(orderId);
    }

    private void insertPaymentOrderHistoryList(
            List<PaymentOrderStatusOutPut> paymentOrderStatusList,
            PaymentOrderStatus newPaymentStatus,
            String reason
    ) {
        List<PaymentOrderHistory> paymentOrderHistories = PaymentOrderHistory.of(paymentOrderStatusList, newPaymentStatus, reason);
        paymentOrderHistoryRepository.saveAll(paymentOrderHistories);
    }

    private void updatePaymentOrderStatusByOrderId(String orderId, PaymentOrderStatus paymentStatus) {
        paymentOrderRepository.updatePaymentOrderStatusByOrderId(orderId, paymentStatus);
    }

    private void updatePaymentEventExtraDetails(
            String orderId,
            String paymentKey,
            LocalDateTime approvedDateTime,
            boolean isPaymentDone
    ) {
        paymentEventRepository.updatePaymentEventExtraDetails(
                orderId,
                paymentKey,
                approvedDateTime,
                isPaymentDone
        );
    }

    @Transactional
    public void updateIsWalletDoneByOrderId(String orderId, boolean isWalletDone) {
        paymentEventRepository.updateIsWalletDoneByOrderId(orderId, isWalletDone);
    }

    @SneakyThrows
    private void insertOutBox(PaymentEventMessage paymentEventMessage) {
        ObjectMapper objectMapper = new ObjectMapper();

        OutBox outBox = OutBox.of(
                OutBoxStatus.INIT,
                (String) paymentEventMessage.getPayload().get("orderId"),
                paymentEventMessage.getType().name(),
                (paymentEventMessage.getMetadata().get("partitionKey") != null) ? Integer.parseInt((String) paymentEventMessage.getMetadata().get("partitionKey")) : 0,
                objectMapper.writeValueAsString(paymentEventMessage.getPayload()),
                objectMapper.writeValueAsString(paymentEventMessage.getMetadata())

        );

        outBoxRepository.save(outBox);
    }

    @Override
    public List<PaymentOrderWithSellerOutPut> selectPaymentOrderListWithSellerByOrderId(String orderId) {
        return paymentOrderRepository.selectPaymentOrderListWithSellerByOrderId(orderId);
    }
}
