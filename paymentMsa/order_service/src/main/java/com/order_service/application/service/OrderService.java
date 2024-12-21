package com.order_service.application.service;

import com.common.UseCase;
import com.order_service.application.port.in.RequestOrderUseCase;
import com.order_service.application.port.out.GetOrderPort;
import com.order_service.application.port.out.PaymentOrderRedisPort;
import com.order_service.domain.PaymentOrderWithSellerOutPut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class OrderService implements RequestOrderUseCase {

    private final GetOrderPort getOrderPort;
    private final PaymentOrderRedisPort paymentOrderRedisPort;

    @Override
    public List<PaymentOrderWithSellerOutPut> selectPaymentOrderListWithSellerByOrderId(String orderId) {
        return getOrderPort.selectPaymentOrderListWithSellerByOrderId(orderId);
    }

    @Override
    public void selectOrderTotalAmountGroupingProductBySellerNo(long sellerNo) {
        paymentOrderRedisPort.selectOrderTotalAmountGroupingProductBySellerNo(sellerNo);
    }
}
