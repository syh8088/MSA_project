package kiwi.shop.order.application.port.out;

import kiwi.shop.order.domain.PaymentOrderWithSellerOutPut;

import java.util.List;

public interface GetOrderPort {

    List<PaymentOrderWithSellerOutPut> selectPaymentOrderListWithSellerByOrderId(String orderId);
}
