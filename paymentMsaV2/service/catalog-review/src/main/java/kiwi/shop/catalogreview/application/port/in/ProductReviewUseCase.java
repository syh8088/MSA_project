package kiwi.shop.catalogreview.application.port.in;

import kiwi.shop.catalogreview.domain.InsertProductReviewCommand;

public interface ProductReviewUseCase {

    void insertCatalogReview(InsertProductReviewCommand insertProductReviewCommand);

    void deleteProductReview(long catalogLikeNo);
}
