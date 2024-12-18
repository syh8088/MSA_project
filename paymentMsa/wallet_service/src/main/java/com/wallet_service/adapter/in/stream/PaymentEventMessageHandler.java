package com.wallet_service.adapter.in.stream;

import com.wallet_service.application.port.in.SettlementUseCase;
import com.wallet_service.domain.PaymentEventMessage;
import com.wallet_service.domain.WalletEventMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentEventMessageHandler implements Consumer<Message<PaymentEventMessage>> {

    private final SettlementUseCase settlementUseCase;
    private final StreamBridge streamBridge;

    @SneakyThrows
    @Override
    public void accept(Message<PaymentEventMessage> clusterMessage) {

//        throw new Exception("ttt");
        log.info("key: {}", clusterMessage.getHeaders().get(KafkaHeaders.RECEIVED_KEY));
        PaymentEventMessage paymentEventMessage = clusterMessage.getPayload();
        log.info("payload: {}", paymentEventMessage);
        WalletEventMessage walletEventMessage = settlementUseCase.settlementProcess(paymentEventMessage.orderId());

        streamBridge.send("wallet", MessageBuilder.withPayload(walletEventMessage).build());
    }
}