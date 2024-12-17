package com.order_service.application.port.out;

import com.order_service.domain.PaymentOrderWithSellerOutPut;

import java.util.List;

public interface GetOrderPort {

    List<PaymentOrderWithSellerOutPut> selectPaymentOrderListWithSellerByOrderId(String orderId);
}
