package kiwi.shop.cataloglike.application.service;

import kiwi.shop.cataloglike.application.port.in.CatalogLikeUseCase;
import kiwi.shop.cataloglike.application.port.out.CatalogLikePort;
import kiwi.shop.cataloglike.common.UseCase;
import kiwi.shop.cataloglike.domain.CatalogLikeCommand;
import kiwi.shop.cataloglike.domain.CatalogUnLikeCommand;
import kiwi.shop.common.event.EventType;
import kiwi.shop.common.event.payload.ProductLikedEventPayload;
import kiwi.shop.common.outboxmessagerelay.OutboxEventPublisher;
import kiwi.shop.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class CatalogLikeService implements CatalogLikeUseCase {

    private final CatalogLikePort catalogLikePort;

    private final OutboxEventPublisher outboxEventPublisher;

    private final Snowflake snowflake = new Snowflake();

    @Transactional
    @Override
    public void like(long productNo, long memberNo) {

        long nextId = snowflake.nextId();
        LocalDateTime now = LocalDateTime.now();
        CatalogLikeCommand catalogLikeCommand = CatalogLikeCommand.of(nextId, productNo, memberNo, now);
        catalogLikePort.like(catalogLikeCommand);

        ProductLikedEventPayload productLikedEventPayload = ProductLikedEventPayload.of(nextId, productNo, memberNo, now);
        outboxEventPublisher.publish(
                EventType.PRODUCT_LIKED,
                productLikedEventPayload,
                String.valueOf(nextId)
        );
    }

    @Transactional
    @Override
    public void unlike(long productNo, long memberNo) {

        CatalogUnLikeCommand catalogUnLikeCommand = CatalogUnLikeCommand.of(productNo, memberNo);
        catalogLikePort.unlike(catalogUnLikeCommand);
    }
}
