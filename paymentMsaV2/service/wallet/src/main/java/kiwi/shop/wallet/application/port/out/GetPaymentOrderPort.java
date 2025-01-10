package kiwi.shop.wallet.application.port.out;

import kiwi.shop.wallet.domain.PaymentOrderWithSellerOutPut;

import java.util.List;

public interface GetPaymentOrderPort {

    List<PaymentOrderWithSellerOutPut> selectPaymentOrderListWithSellerByOrderId(String orderId);

}
