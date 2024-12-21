package com.order_service.application.service;

import com.common.UseCase;
import com.order_service.application.port.in.WalletResultEventMessageUseCase;
import com.order_service.application.port.out.OutBoxStatusUpdatePort;
import com.order_service.application.port.out.PaymentStatusUpdatePort;
import com.order_service.domain.OutBoxStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class WalletResultEventMessageService implements WalletResultEventMessageUseCase {

    private final PaymentStatusUpdatePort paymentStatusUpdatePort;
    private final OutBoxStatusUpdatePort outBoxStatusUpdatePort;

    @Override
    public void execute(String orderId) {

        paymentStatusUpdatePort.updateIsWalletDoneByOrderId(orderId, true);
        outBoxStatusUpdatePort.updateStatusByIdempotencyKey(orderId, OutBoxStatus.SUCCESS);
    }
}
