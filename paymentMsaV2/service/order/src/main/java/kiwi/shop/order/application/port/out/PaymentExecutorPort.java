package kiwi.shop.order.application.port.out;


import kiwi.shop.order.domain.PaymentConfirmCommand;
import kiwi.shop.order.domain.PaymentExecutionResultOutPut;

public interface PaymentExecutorPort {

    PaymentExecutionResultOutPut paymentConfirm(PaymentConfirmCommand request);
}
