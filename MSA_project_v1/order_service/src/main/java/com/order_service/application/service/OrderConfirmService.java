package com.order_service.application.service;

import com.common.UseCase;
import com.order_service.application.port.in.RequestOrderConfirmUseCase;
import com.order_service.application.port.out.PaymentExecutorPort;
import com.order_service.application.port.out.PaymentStatusUpdatePort;
import com.order_service.domain.PaymentConfirmCommand;
import com.order_service.domain.PaymentExecutionResultOutPut;
import com.order_service.domain.message.PaymentEventMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class OrderConfirmService implements RequestOrderConfirmUseCase {

    private final PaymentExecutorPort paymentExecutorPort;
    private final PaymentStatusUpdatePort paymentStatusUpdatePort;
    private final StreamBridge streamBridge;
    private static final String bindingName = "send-out-0";

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void paymentConfirm(PaymentConfirmCommand request) {

        PaymentExecutionResultOutPut paymentExecutionResult = paymentExecutorPort.paymentConfirm(request);
        PaymentEventMessage paymentEventMessage = paymentStatusUpdatePort.updatePaymentStatus(paymentExecutionResult);

        if (!Objects.isNull(paymentEventMessage)) {
            streamBridge.send(bindingName, MessageBuilder
                    .withPayload(paymentEventMessage)
                    .setHeader(KafkaHeaders.KEY, paymentEventMessage.getMetadata().get("partitionKey"))
                    .build()
            );
        }
    }
}
