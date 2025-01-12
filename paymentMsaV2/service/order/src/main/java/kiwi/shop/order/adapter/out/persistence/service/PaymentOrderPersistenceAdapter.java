package kiwi.shop.order.adapter.out.persistence.service;

import kiwi.shop.common.event.EventType;
import kiwi.shop.common.event.payload.PaymentConfirmEventPayload;
import kiwi.shop.common.outboxmessagerelay.OutboxEventPublisher;
import kiwi.shop.order.adapter.out.persistence.entity.PaymentEvent;
import kiwi.shop.order.adapter.out.persistence.entity.PaymentOrderHistory;
import kiwi.shop.order.adapter.out.persistence.repository.PaymentEventMapper;
import kiwi.shop.order.adapter.out.persistence.repository.PaymentEventRepository;
import kiwi.shop.order.adapter.out.persistence.repository.PaymentOrderHistoryRepository;
import kiwi.shop.order.adapter.out.persistence.repository.PaymentOrderRepository;
import kiwi.shop.order.application.port.out.*;
import kiwi.shop.order.common.WebAdapter;
import kiwi.shop.order.domain.*;
import kiwi.shop.order.domain.message.PaymentEventMessage;
import kiwi.shop.order.domain.message.PaymentEventMessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class PaymentOrderPersistenceAdapter implements PaymentCheckOutPort, PaymentStatusUpdatePort, GetOrderPort {

    private final PaymentEventRepository paymentEventRepository;
    private final PaymentOrderRepository paymentOrderRepository;
    private final PaymentOrderHistoryRepository paymentOrderHistoryRepository;
    private final PaymentEventMapper paymentEventMapper;

    private final OutboxEventPublisher outboxEventPublisher;

    @Override
    public void insertPaymentCheckOut(PaymentEvent paymentEvent) {
        paymentEventRepository.save(paymentEvent);
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
        PaymentConfirmEventPayload payload = PaymentConfirmEventPayload.of(orderId);
        outboxEventPublisher.publish(
                EventType.PAYMENT_CONFIRM,
                payload,
                orderId
        );
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

    @Transactional(readOnly = true)
    @Override
    public List<PaymentOrderWithSellerOutPut> selectPaymentOrderListWithSellerByOrderId(String orderId) {
        return paymentOrderRepository.selectPaymentOrderListWithSellerByOrderId(orderId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<PaymentEventOutPut> selectPaymentEventByMemberNoAndPaymentMethodNo(long memberNo, long paymentMethodNo) {
        return paymentEventRepository.selectPaymentEventByMemberNoAndPaymentMethodNo(memberNo, paymentMethodNo);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PaymentEventOutPut> selectPaymentEventList(long memberNo) {
        return paymentEventRepository.selectPaymentEventListByMemberNo(memberNo);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PaymentEventWithOrderOutPut> selectPaymentEventWithOrderListByMemberNoAndPaymentEventNo(PaymentEventWithOrderListCommand paymentEventWithOrderListCommand) {

        if (Objects.isNull(paymentEventWithOrderListCommand.getPaymentEventNo()) || Objects.isNull(paymentEventWithOrderListCommand.getCreatedDateTime())) {
            return paymentEventMapper.selectFirstPaymentEventWithOrderListByMemberNo(
                    paymentEventWithOrderListCommand.getMemberNo(),
                    paymentEventWithOrderListCommand.getLimit()
            );
        }

        return paymentEventMapper.selectPaymentEventWithOrderListByMemberNoAndPaymentEventNo(
                paymentEventWithOrderListCommand.getPaymentEventNo(),
                paymentEventWithOrderListCommand.getCreatedDateTime(),
                paymentEventWithOrderListCommand.getMemberNo(),
                paymentEventWithOrderListCommand.getLimit()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<PaymentEventWithOrderOutPut> selectPaymentEventWithOrderListByMemberNoAndPaymentEventNo(long memberNo, long paymentEventNo) {
        return paymentEventRepository.selectPaymentEventWithOrderListByMemberNoAndPaymentEventNo(memberNo, paymentEventNo);
    }
}
