package kiwi.shop.order.adapter.out.persistence.repository;


import kiwi.shop.order.domain.PaymentEventOutPut;
import kiwi.shop.order.domain.PaymentEventWithOrderOutPut;

import java.util.List;
import java.util.Optional;

public interface PaymentEventRepositoryCustom {

    List<PaymentEventOutPut> selectPaymentEventList();

    List<PaymentEventOutPut> selectPaymentEventListByMemberNo(long memberNo);

    Optional<PaymentEventOutPut> selectPaymentEventByMemberNoAndPaymentMethodNo(long memberNo, long paymentMethodNo);

    List<PaymentEventWithOrderOutPut> selectPaymentEventWithOrderListByMemberNoAndPaymentEventNo(long memberNo, long paymentEventNo);
}
