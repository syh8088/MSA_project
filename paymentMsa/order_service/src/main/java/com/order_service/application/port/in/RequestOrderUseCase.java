package com.order_service.application.port.in;


import com.order_service.domain.PaymentOrderWithSellerOutPut;

import java.util.List;

public interface RequestOrderUseCase {


    List<PaymentOrderWithSellerOutPut> selectPaymentOrderListWithSellerByOrderId(String orderId);

}
