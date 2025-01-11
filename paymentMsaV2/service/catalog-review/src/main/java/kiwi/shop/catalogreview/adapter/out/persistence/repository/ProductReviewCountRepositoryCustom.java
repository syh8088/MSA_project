package kiwi.shop.catalogreview.adapter.out.persistence.repository;

import kiwi.shop.catalogreview.domain.ProductReviewCountOutPut;

public interface ProductReviewCountRepositoryCustom {

    ProductReviewCountOutPut selectCatalogReviewCountByProductNo(long productNo);
}
