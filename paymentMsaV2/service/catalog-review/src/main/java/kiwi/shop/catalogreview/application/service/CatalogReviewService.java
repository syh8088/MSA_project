package kiwi.shop.catalogreview.application.service;

import kiwi.shop.catalogreview.adapter.out.persistence.entity.CatalogReview;
import kiwi.shop.catalogreview.adapter.out.persistence.entity.CatalogReviewCount;
import kiwi.shop.catalogreview.application.port.in.CatalogReviewUseCase;
import kiwi.shop.catalogreview.application.port.out.CatalogReviewCountPort;
import kiwi.shop.catalogreview.application.port.out.CatalogReviewPort;
import kiwi.shop.catalogreview.common.UseCase;
import kiwi.shop.catalogreview.domain.InsertCatalogReviewCommand;
import kiwi.shop.common.event.EventType;
import kiwi.shop.common.event.payload.ReviewCreatedEventPayload;
import kiwi.shop.common.event.payload.ReviewDeletedEventPayload;
import kiwi.shop.common.outboxmessagerelay.OutboxEventPublisher;
import kiwi.shop.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class CatalogReviewService implements CatalogReviewUseCase {

    private final CatalogReviewPort catalogReviewPort;
    private final CatalogReviewCountPort catalogReviewCountPort;

    private final OutboxEventPublisher outboxEventPublisher;

    private final Snowflake snowflake = new Snowflake();

    @Transactional
    @Override
    public void insertCatalogReview(InsertCatalogReviewCommand insertCatalogReviewCommand) {

        CatalogReview catalogReview = catalogReviewPort.insertCatalogReview(insertCatalogReviewCommand);
        CatalogReviewCount catalogReviewCount
                = catalogReviewCountPort.increase(
                        insertCatalogReviewCommand.getProductNo(),
                        insertCatalogReviewCommand.getStarRatingType()
                );

        ReviewCreatedEventPayload reviewCreatedEventPayload
                = ReviewCreatedEventPayload.of(
                    catalogReview.getCatalogReviewNo(),
                    catalogReview.getProductNo(),
                    catalogReview.getMemberNo(),
                    catalogReview.getTitle(),
                    catalogReview.getContent(),
                    catalogReview.getStarRating(),
                    catalogReview.getIsDeleted(),
                    catalogReviewCount.getReviewCount(),
                    catalogReviewCount.getReviewStarRatingSum()
                );

        outboxEventPublisher.publish(
                EventType.PRODUCT_CREATED_REVIEW,
                reviewCreatedEventPayload,
                String.valueOf(snowflake.nextId())
        );
    }

    @Transactional
    @Override
    public void deleteCatalogReview(long catalogReviewNo) {

        CatalogReview catalogReview = catalogReviewPort.selectCatalogReviewThenThrowExceptionByCatalogReviewNo(catalogReviewNo);

        catalogReviewPort.deleteCatalogReview(catalogReview.getCatalogReviewNo());
        CatalogReviewCount catalogReviewCount
                = catalogReviewCountPort.decrease(
                        catalogReview.getProductNo(),
                        catalogReview.getStarRating()
                );

        ReviewDeletedEventPayload reviewDeletedEventPayload
                = ReviewDeletedEventPayload.of(
                    catalogReview.getCatalogReviewNo(),
                    catalogReview.getProductNo(),
                    catalogReview.getMemberNo(),
                    catalogReview.getTitle(),
                    catalogReview.getContent(),
                    catalogReview.getStarRating(),
                    catalogReview.getIsDeleted(),
                    catalogReviewCount.getReviewCount(),
                    catalogReviewCount.getReviewStarRatingSum()
                );

        outboxEventPublisher.publish(
                EventType.PRODUCT_DELETED_REVIEW,
                reviewDeletedEventPayload,
                String.valueOf(snowflake.nextId())
        );
    }
}
