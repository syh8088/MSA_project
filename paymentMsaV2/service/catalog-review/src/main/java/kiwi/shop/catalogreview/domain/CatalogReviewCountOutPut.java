package kiwi.shop.catalogreview.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class CatalogReviewCountOutPut {

    private long productNo;
    private long reviewCount;
    private BigDecimal reviewStarRatingSum;

    @QueryProjection
    public CatalogReviewCountOutPut(long productNo, long reviewCount, BigDecimal reviewStarRatingSum) {
        this.productNo = productNo;
        this.reviewCount = reviewCount;
        this.reviewStarRatingSum = reviewStarRatingSum;
    }

    public static CatalogReviewCountOutPut of(long productNo, long reviewCount, BigDecimal reviewStarRatingSum) {

        CatalogReviewCountOutPut catalogReviewCountOutPut = new CatalogReviewCountOutPut();
        catalogReviewCountOutPut.productNo = productNo;
        catalogReviewCountOutPut.reviewCount = reviewCount;
        catalogReviewCountOutPut.reviewStarRatingSum = reviewStarRatingSum;

        return catalogReviewCountOutPut;
    }
}
