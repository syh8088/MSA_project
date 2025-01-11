package kiwi.shop.cataloglike.application.service;

import kiwi.shop.cataloglike.adapter.out.persistence.entity.ProductLike;
import kiwi.shop.cataloglike.application.port.in.ProductLikeUseCase;
import kiwi.shop.cataloglike.application.port.out.ProductLikeCountPort;
import kiwi.shop.cataloglike.application.port.out.ProductLikePort;
import kiwi.shop.cataloglike.common.UseCase;
import kiwi.shop.cataloglike.domain.ProductLikeCommand;
import kiwi.shop.cataloglike.domain.ProductUnLikeCommand;
import kiwi.shop.common.event.EventType;
import kiwi.shop.common.event.payload.ProductLikedEventPayload;
import kiwi.shop.common.event.payload.ProductUnLikedEventPayload;
import kiwi.shop.common.outboxmessagerelay.OutboxEventPublisher;
import kiwi.shop.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class ProductLikeService implements ProductLikeUseCase {

    private final ProductLikePort productLikePort;

    private final ProductLikeCountPort productLikeCountPort;

    private final OutboxEventPublisher outboxEventPublisher;

    private final Snowflake snowflake = new Snowflake();

    @Transactional
    @Override
    public void like(long productNo, long memberNo) {

        long nextId = snowflake.nextId();
        LocalDateTime now = LocalDateTime.now();
        ProductLikeCommand productLikeCommand = ProductLikeCommand.of(nextId, productNo, memberNo, now);

        productLikePort.like(productLikeCommand);
        productLikeCountPort.increase(productNo);

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
            productLikePort.unlike(productUnLikeCommand);

            productLikeCountPort.decrease(productNo);
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
}
