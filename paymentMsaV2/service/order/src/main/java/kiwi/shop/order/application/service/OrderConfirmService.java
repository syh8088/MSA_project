package kiwi.shop.order.application.service;

import kiwi.shop.order.application.port.in.RequestOrderConfirmUseCase;
import kiwi.shop.order.application.port.out.PaymentExecutorPort;
import kiwi.shop.order.application.port.out.PaymentStatusUpdatePort;
import kiwi.shop.order.domain.PaymentConfirmCommand;
import kiwi.shop.order.domain.PaymentExecutionResultOutPut;
import kiwi.shop.order.common.UseCase;
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
