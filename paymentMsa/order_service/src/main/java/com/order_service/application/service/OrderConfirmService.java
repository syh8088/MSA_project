package com.order_service.application.service;

import com.common.UseCase;
import com.order_service.application.port.in.RequestOrderConfirmUseCase;
import com.order_service.application.port.out.PaymentExecutorPort;
import com.order_service.application.port.out.PaymentStatusUpdatePort;
import com.order_service.domain.PaymentConfirmCommand;
import com.order_service.domain.PaymentExecutionResultOutPut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class OrderConfirmService implements RequestOrderConfirmUseCase {

    private final PaymentExecutorPort paymentExecutorPort;
    private final PaymentStatusUpdatePort paymentStatusUpdatePort;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void paymentConfirm(PaymentConfirmCommand request) {

        PaymentExecutionResultOutPut paymentExecutionResult = paymentExecutorPort.paymentConfirm(request);
        paymentStatusUpdatePort.updatePaymentStatus(paymentExecutionResult);
    }
}
