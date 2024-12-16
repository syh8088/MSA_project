package com.order_service.adapter.out.persistence.service;

import com.order_service.adapter.out.persistence.entity.PaymentEvent;
import com.order_service.adapter.out.persistence.repository.PaymentEventRepository;
import com.order_service.adapter.out.persistence.repository.PaymentOrderRepository;
import com.order_service.application.port.out.PaymentCheckOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentOrderPersistenceAdapter implements PaymentCheckOutPort {

    private final PaymentEventRepository paymentEventRepository;
    private final PaymentOrderRepository paymentOrderRepository;

    @Override
    public void insertPaymentCheckOut(PaymentEvent paymentEvent) {
        paymentEventRepository.save(paymentEvent);
        paymentOrderRepository.saveAll(paymentEvent.getPaymentOrderList());
    }
}
