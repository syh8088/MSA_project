package kiwi.shop.order.application.port.in;


import kiwi.shop.order.domain.PaymentEventOutPut;
import kiwi.shop.order.domain.PaymentEventWithOrderListCommand;
import kiwi.shop.order.domain.PaymentEventWithOrderOutPut;
import kiwi.shop.order.domain.PaymentOrderWithSellerOutPut;

import java.util.List;
import java.util.Optional;

public interface RequestOrderUseCase {


    List<PaymentOrderWithSellerOutPut> selectPaymentOrderListWithSellerByOrderId(String orderId);

    Optional<PaymentEventOutPut> selectPaymentEventByMemberNoAndPaymentMethodNo(long memberNo, long paymentMethodNo);

    void selectPaymentOrderList(long memberNo);

    List<PaymentEventWithOrderOutPut> selectPaymentEventWithOrderListByMemberNo(PaymentEventWithOrderListCommand paymentEventWithOrderListCommand);

    List<PaymentEventWithOrderOutPut> selectPaymentEventWithOrderListByMemberNoAndPaymentEventNo(long memberNo, long paymentEventNo);
}
