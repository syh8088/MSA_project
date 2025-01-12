package kiwi.shop.hotcatalog.adapter.in.stream;

import kiwi.shop.common.event.Event;
import kiwi.shop.common.event.EventPayload;
import kiwi.shop.common.event.EventType;
import kiwi.shop.hotcatalog.application.port.in.HotProductUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotCatalogEventConsumer {

    private final HotProductUseCase hotProductUsecase;

    @KafkaListener(topics = {
                EventType.Topic.KIWI_SHOP_PRODUCT_LIKE,
                EventType.Topic.KIWI_SHOP_PRODUCT_REVIEW,
            },
            groupId = "hot-catalog-service"
    )
    public void listen(String message, Acknowledgment ack) {

//        throw new IllegalArgumentException("일부러 Exception 발생");
        log.info("[HotCatalogEventConsumer.listen] received message={}", message);

        Event<EventPayload> event = Event.fromJson(message);
        if (Objects.nonNull(event)) {
            hotProductUsecase.messageEventHandler(event);
        }

        ack.acknowledge();
    }
}
