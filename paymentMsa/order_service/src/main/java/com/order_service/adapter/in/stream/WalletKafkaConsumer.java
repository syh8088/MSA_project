package com.order_service.adapter.in.stream;

import com.order_service.application.port.in.WalletResultEventMessageUseCase;
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

    private final WalletResultEventMessageUseCase walletResultEventMessageUseCase;

    @SneakyThrows
    @Override
    public void accept(Message<WalletEventMessage> clusterMessage) {

        log.info("key: {}", clusterMessage.getHeaders().get(KafkaHeaders.RECEIVED_KEY));
        log.info("payload: {}", clusterMessage);
        WalletEventMessage walletEventMessage = clusterMessage.getPayload();
        String orderId = walletEventMessage.orderId();

        walletResultEventMessageUseCase.execute(orderId);
    }
}