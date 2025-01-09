package kiwi.shop.order.application.port.in;


import kiwi.shop.order.domain.PaymentOrderWithSellerOutPut;

import java.util.List;

public interface RequestOrderUseCase {


    List<PaymentOrderWithSellerOutPut> selectPaymentOrderListWithSellerByOrderId(String orderId);

}
