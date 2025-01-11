package kiwi.shop.cataloglike.application.port.out;

import kiwi.shop.cataloglike.adapter.out.persistence.entity.ProductLike;
import kiwi.shop.cataloglike.domain.ProductLikeCommand;
import kiwi.shop.cataloglike.domain.ProductUnLikeCommand;

import java.util.Optional;

public interface ProductLikePort {


    void like(ProductLikeCommand productLikeCommand);

    void unlike(ProductUnLikeCommand productUnLikeCommand);

    Optional<ProductLike> selectProductLikeByProductNoAndMemberNo(long productNo, long memberNo);
}