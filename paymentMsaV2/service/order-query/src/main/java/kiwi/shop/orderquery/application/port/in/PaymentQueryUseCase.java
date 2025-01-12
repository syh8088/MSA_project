package kiwi.shop.orderquery.application.port.in;

import kiwi.shop.orderquery.domain.PaymentEventQueryModel;
import kiwi.shop.orderquery.domain.PaymentEventResponse;
import kiwi.shop.orderquery.domain.PaymentEventWithOrderListCommand;

import java.util.List;

public interface PaymentQueryUseCase {

    void insertOrderQuery(PaymentEventQueryModel command);

    PaymentEventResponse selectPaymentOrderDetail(long memberNo, long paymentMethodNo);

    List<PaymentEventResponse> selectPaymentEventWithOrderList(PaymentEventWithOrderListCommand paymentEventWithOrderListCommand);
}
