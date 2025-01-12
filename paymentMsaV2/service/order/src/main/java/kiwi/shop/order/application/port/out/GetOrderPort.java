package kiwi.shop.order.application.port.out;

import kiwi.shop.order.domain.PaymentEventOutPut;
import kiwi.shop.order.domain.PaymentEventWithOrderListCommand;
import kiwi.shop.order.domain.PaymentEventWithOrderOutPut;
import kiwi.shop.order.domain.PaymentOrderWithSellerOutPut;

import java.util.List;
import java.util.Optional;

public interface GetOrderPort {

    List<PaymentOrderWithSellerOutPut> selectPaymentOrderListWithSellerByOrderId(String orderId);

    Optional<PaymentEventOutPut> selectPaymentEventByMemberNoAndPaymentMethodNo(long memberNo, long paymentMethodNo);

    List<PaymentEventOutPut> selectPaymentEventList(long memberNo);

    List<PaymentEventWithOrderOutPut> selectPaymentEventWithOrderListByMemberNoAndPaymentEventNo(PaymentEventWithOrderListCommand paymentEventWithOrderListCommand);
    List<PaymentEventWithOrderOutPut> selectPaymentEventWithOrderListByMemberNoAndPaymentEventNo(long memberNo, long paymentMethodNo);
}
