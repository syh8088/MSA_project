package com.order_service.application.port.out;

import com.order_service.domain.PaymentExecutionResultOutPut;
import com.order_service.domain.message.PaymentEventMessage;

public interface PaymentStatusUpdatePort {

    void updatePaymentStatusToExecuting(String orderId, String paymentKey);

    PaymentEventMessage updatePaymentStatus(PaymentExecutionResultOutPut command);
}