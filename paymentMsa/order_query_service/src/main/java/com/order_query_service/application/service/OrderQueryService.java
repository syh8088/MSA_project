package com.order_query_service.application.service;

import com.common.UseCase;
import com.order_query_service.adapter.in.web.response.OrderTotalAmountGroupingProduct;
import com.order_query_service.application.port.in.GetOrderQueryUseCase;
import com.order_query_service.application.port.in.OrderQuerySellerAmountSumGroupingProductUseCase;
import com.order_query_service.application.port.out.GetPaymentOrderPort;
import com.order_query_service.application.port.out.PaymentOrderRedisPort;
import com.order_query_service.domain.PaymentOrderWithSellerOutPut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class OrderQueryService implements OrderQuerySellerAmountSumGroupingProductUseCase, GetOrderQueryUseCase {

    private final PaymentOrderRedisPort paymentOrderRedisPort;
    private final GetPaymentOrderPort getPaymentOrderPort;

    @Override
    public void updateSellerAmountSum(String orderId) {

        List<PaymentOrderWithSellerOutPut> paymentOrderList = getPaymentOrderPort.selectPaymentOrderListWithSellerByOrderId(orderId);

        paymentOrderRedisPort.updatePaymentOrderAmountSumGropingSellerNo(orderId, paymentOrderList);

    }

    @Override
    public List<OrderTotalAmountGroupingProduct> selectOrderTotalAmountGroupingProductBySellerNo(long sellerNo) {

        return paymentOrderRedisPort.selectOrderTotalAmountGroupingProductBySellerNo(sellerNo);
    }
}
