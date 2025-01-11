package kiwi.shop.catalogreview.adapter.out.persistence.repository;

import kiwi.shop.catalogreview.domain.CatalogReviewCountOutPut;

public interface CatalogReviewCountRepositoryCustom {

    CatalogReviewCountOutPut selectCatalogReviewCountByProductNo(long productNo);
}
