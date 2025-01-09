package kiwi.shop.order.adapter.out.web.toss;

import kiwi.shop.order.adapter.out.web.toss.executor.PaymentExecutor;
import kiwi.shop.order.application.port.out.PaymentExecutorPort;
import kiwi.shop.order.common.WebAdapter;
import kiwi.shop.order.domain.PaymentConfirmCommand;
import kiwi.shop.order.domain.PaymentExecutionResultOutPut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class PaymentExecutorWebAdapter implements PaymentExecutorPort {

    private final PaymentExecutor paymentExecutor;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public PaymentExecutionResultOutPut paymentConfirm(PaymentConfirmCommand request) {

        return paymentExecutor.paymentExecutor(request);
    }


}
