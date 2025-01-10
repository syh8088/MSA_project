package kiwi.shop.cataloglike.application.port.out;

import kiwi.shop.cataloglike.domain.CatalogLikeCommand;
import kiwi.shop.cataloglike.domain.CatalogUnLikeCommand;

public interface CatalogLikePort {


    void like(CatalogLikeCommand catalogLikeCommand);

    void unlike(CatalogUnLikeCommand catalogUnLikeCommand);

}