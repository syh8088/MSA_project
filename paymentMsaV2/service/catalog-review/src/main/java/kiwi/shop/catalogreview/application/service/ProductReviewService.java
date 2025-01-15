package kiwi.shop.catalogreview.application.service;

import kiwi.shop.catalogreview.adapter.out.persistence.entity.ProductReview;
import kiwi.shop.catalogreview.adapter.out.persistence.entity.ProductReviewCount;
import kiwi.shop.catalogreview.adapter.out.persistence.exception.RetryExhaustedWithOptimisticLockingFailureException;
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
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class ProductReviewService implements ProductReviewUseCase {

    private final ProductReviewPort productReviewPort;
    private final ProductReviewCountPort productReviewCountPort;

    private final OutboxEventPublisher outboxEventPublisher;

    private final List<UpdateProductReviewHandler> updateProductReviewHandlers;

    private final Snowflake snowflake = new Snowflake();

    @Transactional
    @Override
    public void insertCatalogReview(InsertProductReviewCommand insertProductReviewCommand) {

        ProductReview productReview = productReviewPort.insertCatalogReview(insertProductReviewCommand);

        Optional<ProductReviewCount> optionalProductReviewCount = Optional.empty();
        try {
            optionalProductReviewCount
                    = productReviewCountPort.increase(
                    insertProductReviewCommand.getProductNo(),
                    insertProductReviewCommand.getStarRatingType().getStarRating()
            );
        }
        catch (ObjectOptimisticLockingFailureException e) {
            this.retrySaveOperation(UpdateProductReviewHandler.UpdateType.DELETE, productReview.getProductNo(), productReview.getStarRating());
        }

       if (optionalProductReviewCount.isPresent()) {

           ProductReviewCount productReviewCount = optionalProductReviewCount.get();
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
    }

    @Transactional
    @Override
    public void deleteProductReview(long productReviewNo) {

        ProductReview productReview = productReviewPort.selectCatalogReviewThenThrowExceptionByProductReviewNo(productReviewNo);

        productReviewPort.deleteProductReview(productReview.getProductReviewNo());

        Optional<ProductReviewCount> optionalProductReviewCount = Optional.empty();
        try {
            optionalProductReviewCount
                    = productReviewCountPort.decrease(
                    productReview.getProductNo(),
                    productReview.getStarRating()
            );
        }
        catch (ObjectOptimisticLockingFailureException e) {
            this.retrySaveOperation(UpdateProductReviewHandler.UpdateType.DELETE, productReview.getProductNo(), productReview.getStarRating());
        }

        if (optionalProductReviewCount.isPresent()) {

            ProductReviewCount productReviewCount = optionalProductReviewCount.get();
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


    /**
     * 재시도를 할 때 최신 상태의 지갑을 기반으로 지갑 상태를 업데이트하도록 로직
     */
    private void performSaveOperationWithRecent(UpdateProductReviewHandler.UpdateType updateType, long productNo, long starRating) {

        UpdateProductReviewHandler updateProductReviewHandler
                = UpdateProductReviewHandler.getHandlerUpdateProductReviewServices(updateType, updateProductReviewHandlers);

        updateProductReviewHandler.execute(productNo, starRating);
    }

    /**
     * 재시도 함수는 최대 3번만 제시도하도록 만들고 제시도 사이에 충돌을 방지하기 위해 약간의 지연 시간을 준다.
     */
    private void retrySaveOperation(UpdateProductReviewHandler.UpdateType updateType, long productNo, long starRating) {

        int maxRetries = 3;
        int baseDelay = 100;
        int retryCount = 0;

        while (true) {
            try {
                this.performSaveOperationWithRecent(updateType, productNo, starRating);
                break;
            }
            catch (ObjectOptimisticLockingFailureException e) {
                // 제시도 할 때도 만약에 충돌이 발생한다면 제시도 횟수를 초과했는지 검사하고 초과했다면
                // 제시도가 모두 소진되었다는 Exception 발생 되도록 한다.
                if (++retryCount > maxRetries) {
                    throw new RetryExhaustedWithOptimisticLockingFailureException(e.getMessage() != null ? e.getMessage() : "exhausted retry count.");
                }

                // 만약에 제시도 횟수를 초과하진 않았더라면 충돌을 방지하기 위해서 베이스 딜레이와 지터 값을 통해 다음 제시도까지 약간 대기하도록 한다.
                this.waitForNextRetry(baseDelay);
            }
        }
    }

    private void waitForNextRetry(int baseDelay) {

        long jitter = (long) (Math.random() * baseDelay);

        try {
            Thread.sleep(jitter);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted during retry wait", e);
        }
    }

}
