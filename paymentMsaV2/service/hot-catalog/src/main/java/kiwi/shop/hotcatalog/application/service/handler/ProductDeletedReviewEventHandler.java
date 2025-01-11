package kiwi.shop.hotcatalog.application.service.handler;

import kiwi.shop.common.event.Event;
import kiwi.shop.common.event.EventType;
import kiwi.shop.common.event.payload.ReviewDeletedEventPayload;
import kiwi.shop.hotcatalog.application.port.out.CatalogReviewCountPort;
import kiwi.shop.hotcatalog.common.TimeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductDeletedReviewEventHandler implements EventHandler<ReviewDeletedEventPayload> {

    private final CatalogReviewCountPort catalogReviewCountPort;

    @Override
    public void handle(Event<ReviewDeletedEventPayload> event) {

        ReviewDeletedEventPayload payload = event.getPayload();

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
    public boolean supports(Event<ReviewDeletedEventPayload> event) {
        return EventType.PRODUCT_DELETED_REVIEW == event.getType();
    }

    @Override
    public Long selectProductNo(Event<ReviewDeletedEventPayload> event) {
        return event.getPayload().getProductNo();
    }
}
