package com.order_service.adapter.out.persistence.repository;


import com.order_service.application.port.out.PaymentOrderStatusOutPut;

import java.util.List;

public interface PaymentOrderRepositoryCustom {

    List<PaymentOrderStatusOutPut> selectPaymentOrderStatusListByOrderId(String orderId);
}
