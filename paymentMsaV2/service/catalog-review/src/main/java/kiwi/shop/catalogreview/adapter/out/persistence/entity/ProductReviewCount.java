package kiwi.shop.catalogreview.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_review_counts")
public class ProductReviewCount extends CommonEntity {

    @Id
    @Column(name = "product_no")
    private Long productNo;

    @Column(name = "review_count")
    private long reviewCount;

    @Column(name = "review_star_rating_sum")
    private long reviewStarRatingSum;

    @Version
    @Column(name = "version")
    private Long version;

    @Builder
    private ProductReviewCount(Long productNo, long reviewCount, long reviewStarRatingSum) {
        this.productNo = productNo;
        this.reviewCount = reviewCount;
        this.reviewStarRatingSum = reviewStarRatingSum;
    }

    public static ProductReviewCount of(long productNo, long reviewCount, long reviewStarRatingSum) {
        return ProductReviewCount.builder()
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

    public void reviewStarRatingIncrease(long reviewStarRating) {
        this.reviewStarRatingSum = reviewStarRatingSum + reviewStarRating;
    }

    public void reviewStarRatingDecrease(long reviewStarRating) {
        this.reviewStarRatingSum = reviewStarRatingSum - reviewStarRating;
    }
}
