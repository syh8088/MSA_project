package kiwi.shop.catalogreview.application.port.out;

import kiwi.shop.catalogreview.domain.InsertCatalogReviewCommand;

public interface CatalogReviewPort {

    void insertCatalogReview(InsertCatalogReviewCommand insertCatalogReviewCommand);

}
