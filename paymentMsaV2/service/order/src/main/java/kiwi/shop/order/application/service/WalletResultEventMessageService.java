package kiwi.shop.order.application.service;

import kiwi.shop.order.application.port.in.WalletResultEventMessageUseCase;
import kiwi.shop.order.application.port.out.OutBoxStatusUpdatePort;
import kiwi.shop.order.application.port.out.PaymentStatusUpdatePort;
import kiwi.shop.order.common.UseCase;
import kiwi.shop.order.domain.OutBoxStatus;
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
