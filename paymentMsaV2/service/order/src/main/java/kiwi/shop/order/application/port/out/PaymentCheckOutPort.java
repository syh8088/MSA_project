package kiwi.shop.order.application.port.out;

import kiwi.shop.order.adapter.out.persistence.entity.PaymentEvent;

public interface PaymentCheckOutPort {

    void insertPaymentCheckOut(PaymentEvent paymentEvent);
}
