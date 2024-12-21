package com.order_service.application.port.out;

public interface PaymentOrderRedisPort {

    void updatePaymentOrderAmountSumGropingSellerNo(String orderId);

    void selectOrderTotalAmountGroupingProductBySellerNo(long sellerNo);

}
