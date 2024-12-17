package com.order_service.adapter.out.web.toss;

import com.common.WebAdapter;
import com.order_service.adapter.out.web.toss.executor.PaymentExecutor;
import com.order_service.application.port.out.PaymentExecutorPort;
import com.order_service.domain.PaymentConfirmCommand;
import com.order_service.domain.PaymentExecutionResultOutPut;
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
