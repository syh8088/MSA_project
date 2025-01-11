package kiwi.shop.catalogreview.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ProductReviewCountOutPut {

    private long productNo;
    private long reviewCount;
    private long reviewStarRatingSum;

    @QueryProjection
    public ProductReviewCountOutPut(long productNo, long reviewCount, long reviewStarRatingSum) {
        this.productNo = productNo;
        this.reviewCount = reviewCount;
        this.reviewStarRatingSum = reviewStarRatingSum;
    }

    public static ProductReviewCountOutPut of(long productNo, long reviewCount, long reviewStarRatingSum) {

        ProductReviewCountOutPut productReviewCountOutPut = new ProductReviewCountOutPut();
        productReviewCountOutPut.productNo = productNo;
        productReviewCountOutPut.reviewCount = reviewCount;
        productReviewCountOutPut.reviewStarRatingSum = reviewStarRatingSum;

        return productReviewCountOutPut;
    }
}
