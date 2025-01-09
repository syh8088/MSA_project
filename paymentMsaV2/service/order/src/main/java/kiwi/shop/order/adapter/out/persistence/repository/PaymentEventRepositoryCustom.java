package kiwi.shop.order.adapter.out.persistence.repository;


import kiwi.shop.order.application.port.out.PaymentEventOutPut;

import java.util.List;

public interface PaymentEventRepositoryCustom {

    List<PaymentEventOutPut> selectPaymentEventList();

}
