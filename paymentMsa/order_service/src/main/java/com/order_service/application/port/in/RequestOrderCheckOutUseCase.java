package com.order_service.application.port.in;

import com.order_service.domain.PaymentCheckOutResponse;

public interface RequestOrderCheckOutUseCase {


    PaymentCheckOutResponse paymentCheckOut(PaymentCheckOutCommand request);
}
