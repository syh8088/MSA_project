package kiwi.shop.hotcatalog.adapter.in.stream;


import kiwi.shop.common.event.Event;
import kiwi.shop.common.event.EventType;
import kiwi.shop.common.event.payload.ProductUnLikedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductUnlikedEventHandler implements EventHandler<ProductUnLikedEventPayload> {

    @Override
    public void handle(Event<ProductUnLikedEventPayload> event) {

        ProductUnLikedEventPayload payload = event.getPayload();
        articleLikeCountRepository.createOrUpdate(
                payload.getArticleId(),
                payload.getArticleLikeCount(),
                TimeCalculatorUtils.calculateDurationToMidnight()
        );
    }

    @Override
    public boolean supports(Event<ProductUnLikedEventPayload> event) {
        return EventType.PRODUCT_UNLIKED == event.getType();
    }

    @Override
    public Long selectProductNo(Event<ProductUnLikedEventPayload> event) {
        return event.getPayload().getProductNo();
    }
}
