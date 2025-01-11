package kiwi.shop.hotcatalog.adapter.in.stream;

import kiwi.shop.common.event.Event;
import kiwi.shop.common.event.EventType;
import kiwi.shop.common.event.payload.ProductLikedEventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductLikedEventHandler implements EventHandler<ProductLikedEventPayload> {


    @Override
    public void handle(Event<ProductLikedEventPayload> event) {

        ProductLikedEventPayload payload = event.getPayload();

        articleLikeCountRepository.createOrUpdate(
                payload.getArticleId(),
                payload.getArticleLikeCount(),
                TimeCalculatorUtils.calculateDurationToMidnight()
        );
    }

    @Override
    public boolean supports(Event<ProductLikedEventPayload> event) {
        return EventType.PRODUCT_LIKED == event.getType();
    }

    @Override
    public Long selectProductNo(Event<ProductLikedEventPayload> event) {
        return event.getPayload().getProductNo();
    }
}
