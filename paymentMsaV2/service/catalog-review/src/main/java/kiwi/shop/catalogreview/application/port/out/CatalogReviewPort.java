package kiwi.shop.catalogreview.application.port.out;

import kiwi.shop.catalogreview.adapter.out.persistence.entity.CatalogReview;
import kiwi.shop.catalogreview.domain.InsertCatalogReviewCommand;

public interface CatalogReviewPort {

    CatalogReview selectCatalogReviewThenThrowExceptionByCatalogReviewNo(long catalogReviewNo);

    CatalogReview insertCatalogReview(InsertCatalogReviewCommand insertCatalogReviewCommand);

    void deleteCatalogReview(long catalogReviewNo);
}
