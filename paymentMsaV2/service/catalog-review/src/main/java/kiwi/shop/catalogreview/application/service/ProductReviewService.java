package kiwi.shop.catalogreview.application.service;

import kiwi.shop.catalogreview.adapter.out.persistence.entity.ProductReview;
import kiwi.shop.catalogreview.adapter.out.persistence.entity.ProductReviewCount;
import kiwi.shop.catalogreview.application.port.in.ProductReviewUseCase;
import kiwi.shop.catalogreview.application.port.out.ProductReviewCountPort;
import kiwi.shop.catalogreview.application.port.out.ProductReviewPort;
import kiwi.shop.catalogreview.common.UseCase;
import kiwi.shop.catalogreview.domain.InsertProductReviewCommand;
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
public class ProductReviewService implements ProductReviewUseCase {

    private final ProductReviewPort productReviewPort;
    private final ProductReviewCountPort productReviewCountPort;

    private final OutboxEventPublisher outboxEventPublisher;

    private final Snowflake snowflake = new Snowflake();

    @Transactional
    @Override
    public void insertCatalogReview(InsertProductReviewCommand insertProductReviewCommand) {

        ProductReview productReview = productReviewPort.insertCatalogReview(insertProductReviewCommand);
        ProductReviewCount productReviewCount
                = productReviewCountPort.increase(
                        insertProductReviewCommand.getProductNo(),
                        insertProductReviewCommand.getStarRatingType()
                );

        ReviewCreatedEventPayload reviewCreatedEventPayload
                = ReviewCreatedEventPayload.of(
                    productReview.getProductReviewNo(),
                    productReview.getProductNo(),
                    productReview.getMemberNo(),
                    productReview.getTitle(),
                    productReview.getContent(),
                    productReview.getStarRating(),
                    productReview.getIsDeleted(),
                    productReviewCount.getReviewCount(),
                    productReviewCount.getReviewStarRatingSum()
                );

        outboxEventPublisher.publish(
                EventType.PRODUCT_CREATED_REVIEW,
                reviewCreatedEventPayload,
                String.valueOf(snowflake.nextId())
        );
    }

    @Transactional
    @Override
    public void deleteProductReview(long productReviewNo) {

        ProductReview productReview = productReviewPort.selectCatalogReviewThenThrowExceptionByProductReviewNo(productReviewNo);

        productReviewPort.deleteProductReview(productReview.getProductReviewNo());
        ProductReviewCount productReviewCount
                = productReviewCountPort.decrease(
                        productReview.getProductNo(),
                        productReview.getStarRating()
                );

        ReviewDeletedEventPayload reviewDeletedEventPayload
                = ReviewDeletedEventPayload.of(
                    productReview.getProductReviewNo(),
                    productReview.getProductNo(),
                    productReview.getMemberNo(),
                    productReview.getTitle(),
                    productReview.getContent(),
                    productReview.getStarRating(),
                    productReview.getIsDeleted(),
                    productReviewCount.getReviewCount(),
                    productReviewCount.getReviewStarRatingSum()
                );

        outboxEventPublisher.publish(
                EventType.PRODUCT_DELETED_REVIEW,
                reviewDeletedEventPayload,
                String.valueOf(snowflake.nextId())
        );
    }
}
