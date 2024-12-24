package com.order_service.application.port.out;

import com.order_service.domain.PaymentExecutionResultOutPut;

public interface PaymentStatusUpdatePort {

    void updatePaymentStatusToExecuting(String orderId, String paymentKey);

    void updatePaymentStatus(PaymentExecutionResultOutPut command);

    void updateIsWalletDoneByOrderId(String orderId, boolean isPaymentDone);
}