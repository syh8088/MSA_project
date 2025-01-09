package kiwi.shop.order.application.port.out;

import kiwi.shop.order.domain.PaymentExecutionResultOutPut;

public interface PaymentStatusUpdatePort {

    void updatePaymentStatusToExecuting(String orderId, String paymentKey);

    void updatePaymentStatus(PaymentExecutionResultOutPut command);

    void updateIsWalletDoneByOrderId(String orderId, boolean isPaymentDone);
}