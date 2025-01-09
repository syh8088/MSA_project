package kiwi.shop.order.application.service;

import kiwi.shop.order.application.port.in.RequestOrderUseCase;
import kiwi.shop.order.application.port.out.GetOrderPort;
import kiwi.shop.order.common.UseCase;
import kiwi.shop.order.domain.PaymentOrderWithSellerOutPut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class OrderService implements RequestOrderUseCase {

    private final GetOrderPort getOrderPort;

    @Override
    public List<PaymentOrderWithSellerOutPut> selectPaymentOrderListWithSellerByOrderId(String orderId) {
        return getOrderPort.selectPaymentOrderListWithSellerByOrderId(orderId);
    }
}
