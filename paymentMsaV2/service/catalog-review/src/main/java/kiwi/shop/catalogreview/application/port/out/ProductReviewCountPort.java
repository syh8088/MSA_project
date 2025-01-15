package kiwi.shop.catalogreview.application.port.out;

import kiwi.shop.catalogreview.adapter.out.persistence.entity.ProductReviewCount;
import kiwi.shop.catalogreview.domain.ProductReviewCountOutPut;

import java.util.Optional;

public interface ProductReviewCountPort {

    Optional<ProductReviewCount> increase(long productNo, long starRating);

    Optional<ProductReviewCount> decrease(long productNo, long starRating);

    ProductReviewCountOutPut selectCatalogReviewCountByProductNo(long productNo);
}
