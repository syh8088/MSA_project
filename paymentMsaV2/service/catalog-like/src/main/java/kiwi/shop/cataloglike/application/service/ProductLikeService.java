package kiwi.shop.cataloglike.application.service;

import kiwi.shop.cataloglike.adapter.out.persistence.entity.ProductLike;
import kiwi.shop.cataloglike.adapter.out.persistence.exception.RetryExhaustedWithOptimisticLockingFailureException;
import kiwi.shop.cataloglike.application.port.in.ProductLikeUseCase;
import kiwi.shop.cataloglike.application.port.out.ProductLikeCountPort;
import kiwi.shop.cataloglike.application.port.out.ProductLikePort;
import kiwi.shop.cataloglike.common.UseCase;
import kiwi.shop.cataloglike.domain.ProductLikeCommand;
import kiwi.shop.cataloglike.domain.ProductUnLikeCommand;
import kiwi.shop.cataloglike.domain.UpdateProductLikeCommand;
import kiwi.shop.common.event.EventType;
import kiwi.shop.common.event.payload.ProductLikedEventPayload;
import kiwi.shop.common.event.payload.ProductUnLikedEventPayload;
import kiwi.shop.common.outboxmessagerelay.OutboxEventPublisher;
import kiwi.shop.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class ProductLikeService implements ProductLikeUseCase {

    private final ProductLikePort productLikePort;
    private final ProductLikeCountPort productLikeCountPort;

    private final OutboxEventPublisher outboxEventPublisher;

    private final TransactionTemplate transactionTemplate;

    private List<UpdateProductLikeHandler> updateProductLikeHandlers;

    private final Snowflake snowflake = new Snowflake();

    @Transactional
    @Override
    public void like(long productNo, long memberNo) {

        long nextId = snowflake.nextId();
        LocalDateTime now = LocalDateTime.now();
        ProductLikeCommand productLikeCommand = ProductLikeCommand.of(nextId, productNo, memberNo, now);

        try {
            productLikePort.like(productLikeCommand);
            productLikeCountPort.increase(productNo);
        }
        catch (ObjectOptimisticLockingFailureException e) {
            this.retrySaveOperation(productLikeCommand, productNo);
        }

        long productLikeCount = productLikeCountPort.selectProductLikeCountByProductNo(productNo);

        ProductLikedEventPayload productLikedEventPayload = ProductLikedEventPayload.of(nextId, productNo, memberNo, productLikeCount, now);
        outboxEventPublisher.publish(
                EventType.PRODUCT_LIKED,
                productLikedEventPayload,
                String.valueOf(nextId)
        );
    }

    @Transactional
    @Override
    public void unlike(long productNo, long memberNo) {

        Optional<ProductLike> optionalCatalogLike
                = productLikePort.selectProductLikeByProductNoAndMemberNo(productNo, memberNo);

        if (optionalCatalogLike.isPresent()) {

            ProductLike productLike = optionalCatalogLike.get();

            ProductUnLikeCommand productUnLikeCommand = ProductUnLikeCommand.of(productNo, memberNo);

            try {
                productLikePort.unlike(productUnLikeCommand);
                productLikeCountPort.decrease(productNo);
            }
            catch (ObjectOptimisticLockingFailureException e) {
                this.retrySaveOperation(productUnLikeCommand, productNo);
            }

            long productLikeCount = productLikeCountPort.selectProductLikeCountByProductNo(productNo);

            ProductUnLikedEventPayload productLikedEventPayload
                    = ProductUnLikedEventPayload.of(
                            productLike.getProductLikeNo(),
                            productNo,
                            memberNo,
                            productLikeCount,
                            productLike.getCreatedDateTime()
                    );

            outboxEventPublisher.publish(
                    EventType.PRODUCT_UNLIKED,
                    productLikedEventPayload,
                    String.valueOf(productLike.getProductLikeNo())
            );
        }
    }

    /**
     * 재시도를 할 때 최신 상태의 지갑을 기반으로 지갑 상태를 업데이트하도록 로직
     */
    private void performSaveOperationWithRecent(UpdateProductLikeCommand productLikeCommand, long productNo) {

        UpdateProductLikeHandler updateProductLikeHandler
                = UpdateProductLikeHandler.getHandlerUpdateProductLikeServices(productLikeCommand, updateProductLikeHandlers);

        updateProductLikeHandler.execute(productNo, productLikeCommand);
    }

    /**
     * 재시도 함수는 최대 3번만 제시도하도록 만들고 제시도 사이에 충돌을 방지하기 위해 약간의 지연 시간을 준다.
     */
    private void retrySaveOperation(UpdateProductLikeCommand updateProductLikeCommand, long productNo) {

        int maxRetries = 3;
        int baseDelay = 100;
        int retryCount = 0;

        while (true) {
            try {
                this.performSaveOperationWithRecent(updateProductLikeCommand, productNo);
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
