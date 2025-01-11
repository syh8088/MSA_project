package kiwi.shop.catalogreview.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_review_counts")
public class CatalogReviewCount extends CommonEntity {

    @Id
    @Column(name = "product_no")
    private Long productNo;

    @Column(name = "review_count")
    private long reviewCount;

    @Column(name = "review_star_rating_sum")
    private BigDecimal reviewStarRatingSum;

    @Version
    @Column(name = "version")
    private Long version;

    @Builder
    private CatalogReviewCount(Long productNo, long reviewCount, BigDecimal reviewStarRatingSum) {
        this.productNo = productNo;
        this.reviewCount = reviewCount;
        this.reviewStarRatingSum = reviewStarRatingSum;
    }

    public static CatalogReviewCount of(long productNo, long reviewCount, BigDecimal reviewStarRatingSum) {
        return CatalogReviewCount.builder()
                .productNo(productNo)
                .reviewCount(reviewCount)
                .reviewStarRatingSum(reviewStarRatingSum)
                .build();
    }

    public void reviewCountIncrease() {
        this.reviewCount++;
    }

    public void reviewCountDecrease() {
        this.reviewCount--;
    }

    public void reviewStarRatingIncrease(BigDecimal reviewStarRating) {
        this.reviewStarRatingSum = reviewStarRatingSum.add(reviewStarRating);
    }

    public void reviewStarRatingDecrease(BigDecimal reviewStarRating) {
        this.reviewStarRatingSum = reviewStarRatingSum.subtract(reviewStarRating);
    }
}
