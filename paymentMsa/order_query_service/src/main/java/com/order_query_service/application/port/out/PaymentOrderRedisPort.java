package com.order_query_service.application.port.out;

import com.order_query_service.adapter.in.web.response.OrderTotalAmountGroupingProduct;
import com.order_query_service.domain.PaymentOrderWithSellerOutPut;

import java.util.List;

public interface PaymentOrderRedisPort {

    void updatePaymentOrderAmountSumGropingSellerNo(String orderId, List<PaymentOrderWithSellerOutPut> paymentOrderList);

    List<OrderTotalAmountGroupingProduct> selectOrderTotalAmountGroupingProductBySellerNo(long sellerNo);

}
