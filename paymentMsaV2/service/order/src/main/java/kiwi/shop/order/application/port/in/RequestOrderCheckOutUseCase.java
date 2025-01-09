package kiwi.shop.order.application.port.in;

import kiwi.shop.order.domain.PaymentCheckOutResponse;

public interface RequestOrderCheckOutUseCase {


    PaymentCheckOutResponse paymentCheckOut(PaymentCheckOutCommand request);
}
