package com.wallet_service.adapter.in.stream;

import com.common.StreamAdapter;
import com.wallet_service.application.port.in.SettlementUseCase;
import com.wallet_service.domain.WalletEventMessage;
import com.wallet_service.domain.message.PaymentEventMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.DefaultKafkaHeaderMapper;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.converter.MessagingMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Consumer;

@Slf4j
@StreamAdapter
@RequiredArgsConstructor
public class PaymentEventMessageHandler {

    private final SettlementUseCase settlementUseCase;
    private final StreamBridge streamBridge;

    @Bean
    public MessagingMessageConverter converter() {

        MessagingMessageConverter converter = new MessagingMessageConverter();
        DefaultKafkaHeaderMapper mapper = new DefaultKafkaHeaderMapper();
        mapper.setMapAllStringsOut(false);
        mapper.setEncodeStrings(true);
        converter.setHeaderMapper(mapper);

        return converter;
    }

    @Bean
    public Consumer<Message<PaymentEventMessage>> consume() {
        return message -> {

//        throw new Exception("일부러 Exception 발생 시키기");
            log.info("key: {}", message.getHeaders().get(KafkaHeaders.RECEIVED_KEY));
            PaymentEventMessage paymentEventMessage = message.getPayload();
            log.info("payload: {}", paymentEventMessage);
            WalletEventMessage walletEventMessage = settlementUseCase.settlementProcess(paymentEventMessage.orderId());

            streamBridge.send("wallet", MessageBuilder.withPayload(walletEventMessage).build());
        };
    }
}