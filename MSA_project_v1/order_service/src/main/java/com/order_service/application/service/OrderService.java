package com.order_service.application.service;

import com.common.IdempotencyCreator;
import com.common.UseCase;
import com.order_service.adapter.in.web.PaymentCheckOutRequest;
import com.order_service.adapter.out.persistence.entity.PaymentEvent;
import com.order_service.application.port.in.PaymentCheckOutCommand;
import com.order_service.application.port.in.RequestOrderUseCase;
import com.order_service.application.port.out.GetProductPort;
import com.order_service.application.port.out.PaymentCheckOutOutPut;
import com.order_service.application.port.out.PaymentCheckOutPort;
import com.order_service.application.port.out.ProductOutPut;
import com.order_service.domain.PaymentCheckOutResponse;
import com.order_service.domain.PaymentEventMethod;
import com.order_service.domain.PaymentEventType;
import com.order_service.validator.PaymentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
@Transactional
public class OrderService implements RequestOrderUseCase {

    private final PaymentValidator paymentValidator;
    private final GetProductPort getProductPort;
    private final PaymentCheckOutPort paymentCheckOut;

    @Override
    public PaymentCheckOutResponse paymentCheckOut(PaymentCheckOutCommand request) {

        List<ProductOutPut> productList
                = getProductPort.selectProductListByProductNoList(request.getProductNoList());

        paymentValidator.isExistProduct(productList);

        String idempotency = IdempotencyCreator.create(request);

        PaymentEvent paymentEvent = PaymentEvent.of(
                idempotency,
                PaymentEventMethod.CARD,
                PaymentEventType.NORMAL,
                productList
        );

        paymentCheckOut.insertPaymentCheckOut(paymentEvent);


//        paymentCheckOutCommendService.insertPaymentCheckOut(paymentEvent);
//
//        PaymentCheckOutOutPut paymentCheckOutOutPut = paymentCheckOutQueryService.paymentCheckOut(
//                idempotency,
//                productList
//        );
//
//        return PaymentCheckOutResponse.of(paymentCheckOutOutPut);

        return null;
    }
}
