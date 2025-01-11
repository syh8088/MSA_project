package kiwi.shop.cataloglike.application.port.out;

import kiwi.shop.cataloglike.adapter.out.persistence.entity.CatalogLike;
import kiwi.shop.cataloglike.domain.CatalogLikeCommand;
import kiwi.shop.cataloglike.domain.CatalogUnLikeCommand;

import java.util.Optional;

public interface CatalogLikePort {


    void like(CatalogLikeCommand catalogLikeCommand);

    void unlike(CatalogUnLikeCommand catalogUnLikeCommand);

    Optional<CatalogLike> selectCatalogLikeByProductNoAndMemberNo(long productNo, long memberNo);
}