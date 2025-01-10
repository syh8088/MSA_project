package kiwi.shop.wallet.adapter.in.stream;

import kiwi.shop.common.event.Event;
import kiwi.shop.common.event.EventPayload;
import kiwi.shop.common.event.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventMessageConsumer {

    private final List<EventHandler> eventHandlers;

    @KafkaListener(
            topics = {
                EventType.Topic.KIWI_SHOP_PAYMENT
            },
            groupId = "wallet-service"
    )
    public void listen(String message, Acknowledgment ack) {

        log.info("[PaymentEventMessageConsumer.listen] message={}", message);

        // throw new Exception("일부러 Exception 발생 시키기");
        Event<EventPayload> event = Event.fromJson(message);
        if (!Objects.isNull(event)) {
            this.handleEvent(event);
        }

        ack.acknowledge();
    }

    private void handleEvent(Event<EventPayload> event) {
        for (EventHandler eventHandler : eventHandlers) {
            if (eventHandler.supports(event)) {
                eventHandler.handle(event);
            }
        }
    }
}
