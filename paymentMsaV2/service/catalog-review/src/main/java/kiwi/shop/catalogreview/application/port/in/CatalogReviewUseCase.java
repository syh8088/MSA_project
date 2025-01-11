package kiwi.shop.catalogreview.application.port.in;

import kiwi.shop.catalogreview.domain.InsertCatalogReviewCommand;

public interface CatalogReviewUseCase {

    void insertCatalogReview(InsertCatalogReviewCommand insertCatalogReviewCommand);
}
