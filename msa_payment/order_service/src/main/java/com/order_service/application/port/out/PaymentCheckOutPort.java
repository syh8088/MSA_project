package com.order_service.application.port.out;

import com.order_service.adapter.out.persistence.entity.PaymentEvent;

public interface PaymentCheckOutPort {

    void insertPaymentCheckOut(PaymentEvent paymentEvent);
}
