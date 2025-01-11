package kiwi.shop.catalogreview.application.port.out;

import kiwi.shop.catalogreview.adapter.in.web.request.StarRatingType;
import kiwi.shop.catalogreview.adapter.out.persistence.entity.CatalogReviewCount;
import kiwi.shop.catalogreview.domain.CatalogReviewCountOutPut;

import java.math.BigDecimal;

public interface CatalogReviewCountPort {

    CatalogReviewCount increase(long productNo, StarRatingType starRatingType);

    CatalogReviewCount decrease(long productNo, BigDecimal starRating);

    CatalogReviewCountOutPut selectCatalogReviewCountByProductNo(long productNo);
}
