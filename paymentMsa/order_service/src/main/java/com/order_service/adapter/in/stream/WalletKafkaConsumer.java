package com.order_service.adapter.in.stream;

import com.order_service.application.port.out.OutBoxStatusUpdatePort;
import com.order_service.application.port.out.PaymentStatusUpdatePort;
import com.order_service.domain.OutBoxStatus;
import com.order_service.domain.message.WalletEventMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service("wallet")
@RequiredArgsConstructor
public class WalletKafkaConsumer implements Consumer<Message<WalletEventMessage>> {

    private final PaymentStatusUpdatePort paymentStatusUpdatePort;
    private final OutBoxStatusUpdatePort outBoxStatusUpdatePort;

    @SneakyThrows
    @Override
    public void accept(Message<WalletEventMessage> clusterMessage) {

        log.info("key: {}", clusterMessage.getHeaders().get(KafkaHeaders.RECEIVED_KEY));
        log.info("payload: {}", clusterMessage);
        WalletEventMessage walletEventMessage = clusterMessage.getPayload();
        String orderId = walletEventMessage.orderId();

        paymentStatusUpdatePort.updateIsWalletDoneByOrderId(orderId, true);
        outBoxStatusUpdatePort.updateStatusByIdempotencyKey(orderId, OutBoxStatus.SUCCESS);
    }
}