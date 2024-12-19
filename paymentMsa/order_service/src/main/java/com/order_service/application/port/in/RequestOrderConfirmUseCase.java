package com.order_service.application.port.in;

import com.order_service.domain.PaymentConfirmCommand;

public interface RequestOrderConfirmUseCase {

    void paymentConfirm(PaymentConfirmCommand request);
}
