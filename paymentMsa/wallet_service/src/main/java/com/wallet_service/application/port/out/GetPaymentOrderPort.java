package com.wallet_service.application.port.out;

import com.wallet_service.domain.PaymentOrderWithSellerOutPut;

import java.util.List;

public interface GetPaymentOrderPort {

    List<PaymentOrderWithSellerOutPut> selectPaymentOrderListWithSellerByOrderId(String orderId);

}
