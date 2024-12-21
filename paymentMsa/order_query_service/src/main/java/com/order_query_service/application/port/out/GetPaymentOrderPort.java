package com.order_query_service.application.port.out;

import com.order_query_service.domain.PaymentOrderWithSellerOutPut;

import java.util.List;

public interface GetPaymentOrderPort {

    List<PaymentOrderWithSellerOutPut> selectPaymentOrderListWithSellerByOrderId(String orderId);
}
