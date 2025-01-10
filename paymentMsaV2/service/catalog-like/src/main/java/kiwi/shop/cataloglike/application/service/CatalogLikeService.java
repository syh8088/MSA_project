package kiwi.shop.cataloglike.application.service;

import kiwi.shop.cataloglike.application.port.in.CatalogLikeUseCase;
import kiwi.shop.cataloglike.application.port.out.CatalogLikePort;
import kiwi.shop.cataloglike.common.UseCase;
import kiwi.shop.cataloglike.domain.CatalogLikeCommand;
import kiwi.shop.cataloglike.domain.CatalogUnLikeCommand;
import kiwi.shop.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class CatalogLikeService implements CatalogLikeUseCase {

    private final CatalogLikePort catalogLikePort;

    private final Snowflake snowflake = new Snowflake();

    @Transactional
    @Override
    public void like(long productNo, long memberNo) {

        CatalogLikeCommand catalogLikeCommand = CatalogLikeCommand.of(snowflake.nextId(), productNo, memberNo);
        catalogLikePort.like(catalogLikeCommand);
    }

    @Transactional
    @Override
    public void unlike(long productNo, long memberNo) {

        CatalogUnLikeCommand catalogUnLikeCommand = CatalogUnLikeCommand.of(productNo, memberNo);
        catalogLikePort.unlike(catalogUnLikeCommand);
    }
}
