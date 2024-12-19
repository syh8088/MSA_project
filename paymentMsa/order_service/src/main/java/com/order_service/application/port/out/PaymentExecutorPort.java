package com.order_service.application.port.out;

import com.order_service.domain.PaymentConfirmCommand;
import com.order_service.domain.PaymentExecutionResultOutPut;

public interface PaymentExecutorPort {

    PaymentExecutionResultOutPut paymentConfirm(PaymentConfirmCommand request);
}
