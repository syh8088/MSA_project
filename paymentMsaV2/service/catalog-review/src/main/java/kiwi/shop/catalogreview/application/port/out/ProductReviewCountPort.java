package kiwi.shop.catalogreview.application.port.out;

import kiwi.shop.catalogreview.adapter.in.web.request.StarRatingType;
import kiwi.shop.catalogreview.adapter.out.persistence.entity.ProductReviewCount;
import kiwi.shop.catalogreview.domain.ProductReviewCountOutPut;

public interface ProductReviewCountPort {

    ProductReviewCount increase(long productNo, StarRatingType starRatingType);

    ProductReviewCount decrease(long productNo, long starRating);

    ProductReviewCountOutPut selectCatalogReviewCountByProductNo(long productNo);
}
