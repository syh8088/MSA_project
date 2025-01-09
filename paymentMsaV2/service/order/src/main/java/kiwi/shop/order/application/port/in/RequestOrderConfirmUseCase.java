package kiwi.shop.order.application.port.in;


import kiwi.shop.order.domain.PaymentConfirmCommand;

public interface RequestOrderConfirmUseCase {

    void paymentConfirm(PaymentConfirmCommand request);
}
