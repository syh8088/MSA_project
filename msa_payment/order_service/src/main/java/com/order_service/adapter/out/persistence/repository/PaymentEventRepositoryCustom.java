package com.order_service.adapter.out.persistence.repository;


import com.order_service.application.port.out.PaymentEventOutPut;

import java.util.List;

public interface PaymentEventRepositoryCustom {

    List<PaymentEventOutPut> selectPaymentEventList();

}
