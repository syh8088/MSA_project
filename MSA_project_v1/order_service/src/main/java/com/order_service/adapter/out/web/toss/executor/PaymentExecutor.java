package com.order_service.adapter.out.web.toss.executor;


import com.order_service.domain.PaymentConfirmCommand;
import com.order_service.domain.PaymentExecutionResultOutPut;

public interface PaymentExecutor {

    PaymentExecutionResultOutPut paymentExecutor(PaymentConfirmCommand command);
}