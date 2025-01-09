package kiwi.shop.order.adapter.out.persistence.repository;


import kiwi.shop.order.application.port.out.PaymentOrderStatusOutPut;
import kiwi.shop.order.domain.PaymentOrderWithSellerOutPut;

import java.util.List;

public interface PaymentOrderRepositoryCustom {

    List<PaymentOrderStatusOutPut> selectPaymentOrderStatusListByOrderId(String orderId);

    List<PaymentOrderWithSellerOutPut> selectPaymentOrderListWithSellerByOrderId(String orderId);
}
