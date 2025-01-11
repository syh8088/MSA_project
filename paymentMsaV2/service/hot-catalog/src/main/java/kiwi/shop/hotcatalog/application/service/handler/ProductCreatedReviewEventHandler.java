package kiwi.shop.hotcatalog.application.service.handler;

import kiwi.shop.common.event.Event;
import kiwi.shop.common.event.EventType;
import kiwi.shop.common.event.payload.ReviewCreatedEventPayload;
import kiwi.shop.hotcatalog.application.port.out.CatalogReviewCountPort;
import kiwi.shop.hotcatalog.common.TimeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductCreatedReviewEventHandler implements EventHandler<ReviewCreatedEventPayload> {

    private final CatalogReviewCountPort catalogReviewCountPort;

    @Override
    public void handle(Event<ReviewCreatedEventPayload> event) {

        ReviewCreatedEventPayload payload = event.getPayload();

        catalogReviewCountPort.updateCatalogReviewCount(
                payload.getCatalogReviewNo(),
                payload.getProductNo(),
                payload.getMemberNo(),
                payload.getTitle(),
                payload.getContent(),
                payload.getStarRating(),
                payload.getIsDeleted(),
                payload.getReviewCount(),
                payload.getReviewStarRatingSum(),
                TimeConverter.calculateDurationToMidnight()
        );
    }

    @Override
    public boolean supports(Event<ReviewCreatedEventPayload> event) {
        return EventType.PRODUCT_CREATED_REVIEW == event.getType();
    }

    @Override
    public Long selectProductNo(Event<ReviewCreatedEventPayload> event) {
        return event.getPayload().getProductNo();
    }
}
