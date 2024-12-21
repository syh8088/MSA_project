package com.order_service.adapter.in.stream;

import com.order_service.domain.message.OrderQueryEventMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service("orderquery")
@RequiredArgsConstructor
public class OrderQueryKafkaConsumer implements Consumer<Message<OrderQueryEventMessage>> {

    @SneakyThrows
    @Override
    public void accept(Message<OrderQueryEventMessage> clusterMessage) {

        log.info("key: {}", clusterMessage.getHeaders().get(KafkaHeaders.RECEIVED_KEY));
        log.info("payload: {}", clusterMessage);

        OrderQueryEventMessage orderQueryEventMessage = clusterMessage.getPayload();
        String orderId = orderQueryEventMessage.orderId();

        log.info("consume orderId: {}", orderId);
    }
}