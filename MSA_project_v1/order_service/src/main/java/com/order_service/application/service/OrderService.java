package com.order_service.application.service;

import com.common.IdempotencyCreator;
import com.common.UseCase;
import com.order_service.adapter.out.persistence.entity.PaymentEvent;
import com.order_service.application.port.in.PaymentCheckOutCommand;
import com.order_service.application.port.in.RequestOrderCheckOutUseCase;
import com.order_service.application.port.out.GetCatalogtPort;
import com.order_service.application.port.out.PaymentCheckOutOutPut;
import com.order_service.application.port.out.PaymentCheckOutPort;
import com.order_service.application.port.out.ProductOutPut;
import com.order_service.domain.PaymentCheckOutResponse;
import com.order_service.domain.PaymentEventMethod;
import com.order_service.domain.PaymentEventType;
import com.order_service.validator.PaymentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional
public class OrderService implements RequestOrderCheckOutUseCase {

    private final PaymentValidator paymentValidator;
    private final GetCatalogtPort getCatalogtPort;
    private final PaymentCheckOutPort paymentCheckOut;

    @Override
    public PaymentCheckOutResponse paymentCheckOut(PaymentCheckOutCommand request) {

        List<ProductOutPut> productList
                = getCatalogtPort.selectProductListByProductNoList(request.getProductNoList());

        paymentValidator.isExistProduct(productList);

        String idempotency = IdempotencyCreator.create(request);

        PaymentEvent paymentEvent = PaymentEvent.of(
                idempotency,
                PaymentEventMethod.CARD,
                PaymentEventType.NORMAL,
                productList
        );

        paymentCheckOut.insertPaymentCheckOut(paymentEvent);

        PaymentCheckOutOutPut paymentCheckOutOutPut
                = PaymentCheckOutOutPut.of(
                        paymentEvent.getTotalAmount(),
                        paymentEvent.getOrderId(),
                        paymentEvent.getOrderName()
                );

        return PaymentCheckOutResponse.of(paymentCheckOutOutPut);
    }
}
