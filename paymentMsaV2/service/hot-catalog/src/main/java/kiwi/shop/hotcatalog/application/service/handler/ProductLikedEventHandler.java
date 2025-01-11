package kiwi.shop.hotcatalog.application.service.handler;

import kiwi.shop.common.event.Event;
import kiwi.shop.common.event.EventType;
import kiwi.shop.common.event.payload.ProductLikedEventPayload;
import kiwi.shop.hotcatalog.application.port.out.CatalogLikeCountPort;
import kiwi.shop.hotcatalog.common.TimeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductLikedEventHandler implements EventHandler<ProductLikedEventPayload> {

    private final CatalogLikeCountPort catalogLikeCountPort;


    @Override
    public void handle(Event<ProductLikedEventPayload> event) {

        ProductLikedEventPayload payload = event.getPayload();

        catalogLikeCountPort.updateCatalogLikeCount(
                payload.getProductNo(),
                payload.getProductLikeCount(),
                TimeConverter.calculateDurationToMidnight()
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
