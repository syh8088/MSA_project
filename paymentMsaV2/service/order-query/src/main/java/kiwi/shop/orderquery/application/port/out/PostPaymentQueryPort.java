package kiwi.shop.orderquery.application.port.out;

import kiwi.shop.orderquery.domain.PaymentEventQueryModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PostPaymentQueryPort {

    void insertOrderQuery(PaymentEventQueryModel paymentEventQueryModel);

    Optional<PaymentEventQueryModel> selectPaymentOrder(long memberNo, long paymentEventNo);

    Map<Long, PaymentEventQueryModel> selectPaymentEventWithOrderList(long memberNo, List<Long> paymentEventNoList);

    void insertPaymentEventNoList(long memberNo, long paymentEventNo, int limit);

    List<Long> readAllInfiniteScroll(long memberNo, Long lastPaymentEventNo, int limit);
}
