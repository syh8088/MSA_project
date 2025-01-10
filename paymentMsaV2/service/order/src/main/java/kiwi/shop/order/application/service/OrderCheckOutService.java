package kiwi.shop.order.application.service;

import kiwi.shop.order.adapter.out.persistence.entity.PaymentEvent;
import kiwi.shop.order.application.port.in.PaymentCheckOutCommand;
import kiwi.shop.order.application.port.in.RequestOrderCheckOutUseCase;
import kiwi.shop.order.application.port.out.GetCatalogPort;
import kiwi.shop.order.application.port.out.PaymentCheckOutOutPut;
import kiwi.shop.order.application.port.out.PaymentCheckOutPort;
import kiwi.shop.order.application.port.out.ProductOutPut;
import kiwi.shop.order.common.IdempotencyCreator;
import kiwi.shop.order.common.UseCase;
import kiwi.shop.order.domain.PaymentCheckOutResponse;
import kiwi.shop.order.domain.PaymentEventMethod;
import kiwi.shop.order.domain.PaymentEventType;
import kiwi.shop.order.validator.PaymentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional
public class OrderCheckOutService implements RequestOrderCheckOutUseCase {

    private final PaymentValidator paymentValidator;
    private final GetCatalogPort getCatalogPort;
    private final PaymentCheckOutPort paymentCheckOutPort;

    @Override
    public PaymentCheckOutResponse paymentCheckOut(PaymentCheckOutCommand request) {

        List<ProductOutPut> productList
                = getCatalogPort.selectProductListByProductNoList(request.getProductNoList());

        paymentValidator.isExistProduct(productList);

        String idempotency = IdempotencyCreator.create(request);

        PaymentEvent paymentEvent = PaymentEvent.of(
                idempotency,
                PaymentEventMethod.CARD,
                PaymentEventType.NORMAL,
                productList
        );

        paymentCheckOutPort.insertPaymentCheckOut(paymentEvent);

        PaymentCheckOutOutPut paymentCheckOutOutPut
                = PaymentCheckOutOutPut.of(
                        paymentEvent.getTotalAmount(),
                        paymentEvent.getOrderId(),
                        paymentEvent.getOrderName()
                );

        return PaymentCheckOutResponse.of(paymentCheckOutOutPut);
    }
}
