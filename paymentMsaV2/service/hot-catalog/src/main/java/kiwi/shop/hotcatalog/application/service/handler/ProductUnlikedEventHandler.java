package kiwi.shop.hotcatalog.application.service.handler;


import kiwi.shop.common.event.Event;
import kiwi.shop.common.event.EventType;
import kiwi.shop.common.event.payload.ProductUnLikedEventPayload;
import kiwi.shop.hotcatalog.application.port.out.CatalogLikeCountPort;
import kiwi.shop.hotcatalog.common.TimeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductUnlikedEventHandler implements EventHandler<ProductUnLikedEventPayload> {

    private final CatalogLikeCountPort catalogLikeCountPort;

    @Override
    public void handle(Event<ProductUnLikedEventPayload> event) {

        ProductUnLikedEventPayload payload = event.getPayload();

        catalogLikeCountPort.updateCatalogLikeCount(
                payload.getProductNo(),
                payload.getProductLikeCount(),
                TimeConverter.calculateDurationToMidnight()
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
