package kiwi.shop.cataloglike.application.service;

import kiwi.shop.cataloglike.adapter.out.persistence.entity.CatalogLike;
import kiwi.shop.cataloglike.application.port.in.CatalogLikeUseCase;
import kiwi.shop.cataloglike.application.port.out.CatalogLikeCountPort;
import kiwi.shop.cataloglike.application.port.out.CatalogLikePort;
import kiwi.shop.cataloglike.common.UseCase;
import kiwi.shop.cataloglike.domain.CatalogLikeCommand;
import kiwi.shop.cataloglike.domain.CatalogUnLikeCommand;
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
public class CatalogLikeService implements CatalogLikeUseCase {

    private final CatalogLikePort catalogLikePort;

    private final CatalogLikeCountPort catalogLikeCountPort;

    private final OutboxEventPublisher outboxEventPublisher;

    private final Snowflake snowflake = new Snowflake();

    @Transactional
    @Override
    public void like(long productNo, long memberNo) {

        long nextId = snowflake.nextId();
        LocalDateTime now = LocalDateTime.now();
        CatalogLikeCommand catalogLikeCommand = CatalogLikeCommand.of(nextId, productNo, memberNo, now);
        catalogLikePort.like(catalogLikeCommand);

        catalogLikeCountPort.increase(productNo);
        long productLikeCount = catalogLikeCountPort.selectProductLikeCountByProductNo(productNo);

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

        Optional<CatalogLike> optionalCatalogLike
                = catalogLikePort.selectCatalogLikeByProductNoAndMemberNo(productNo, memberNo);

        if (optionalCatalogLike.isPresent()) {

            CatalogLike catalogLike = optionalCatalogLike.get();

            CatalogUnLikeCommand catalogUnLikeCommand = CatalogUnLikeCommand.of(productNo, memberNo);
            catalogLikePort.unlike(catalogUnLikeCommand);

            catalogLikeCountPort.decrease(productNo);
            long productLikeCount = catalogLikeCountPort.selectProductLikeCountByProductNo(productNo);

            ProductUnLikedEventPayload productLikedEventPayload
                    = ProductUnLikedEventPayload.of(
                            catalogLike.getCatalogLikeNo(),
                            productNo,
                            memberNo,
                            productLikeCount,
                            catalogLike.getCreatedDateTime()
                    );

            outboxEventPublisher.publish(
                    EventType.PRODUCT_UNLIKED,
                    productLikedEventPayload,
                    String.valueOf(catalogLike.getCatalogLikeNo())
            );
        }
    }
}
