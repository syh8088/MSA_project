package kiwi.shop.order.adapter.out.web.toss.executor;


import kiwi.shop.order.domain.PaymentConfirmCommand;
import kiwi.shop.order.domain.PaymentExecutionResultOutPut;

public interface PaymentExecutor {

    PaymentExecutionResultOutPut paymentExecutor(PaymentConfirmCommand command);
}