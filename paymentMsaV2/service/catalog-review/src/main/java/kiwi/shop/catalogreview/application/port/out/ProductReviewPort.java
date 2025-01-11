package kiwi.shop.catalogreview.application.port.out;

import kiwi.shop.catalogreview.adapter.out.persistence.entity.ProductReview;
import kiwi.shop.catalogreview.domain.InsertProductReviewCommand;

public interface ProductReviewPort {

    ProductReview selectCatalogReviewThenThrowExceptionByProductReviewNo(long catalogReviewNo);

    ProductReview insertCatalogReview(InsertProductReviewCommand insertProductReviewCommand);

    void deleteProductReview(long catalogReviewNo);
}
