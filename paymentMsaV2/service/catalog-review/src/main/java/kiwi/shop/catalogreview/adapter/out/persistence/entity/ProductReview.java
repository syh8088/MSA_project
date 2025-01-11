package kiwi.shop.catalogreview.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kiwi.shop.catalogreview.adapter.in.web.request.StarRatingType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_reviews")
public class ProductReview extends CommonEntity {

    @Id
    @Column(name = "product_review_no")
    private Long productReviewNo;

    @Column(name = "product_no")
    private long productNo;

    @Column(name = "member_no")
    private long memberNo;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "star_rating")
    private long starRating;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Builder
    private ProductReview(Long productReviewNo, long productNo, long memberNo, String title, String content, long starRating, Boolean isDeleted) {
        this.productReviewNo = productReviewNo;
        this.productNo = productNo;
        this.memberNo = memberNo;
        this.title = title;
        this.content = content;
        this.starRating = starRating;
        this.isDeleted = isDeleted;
    }

    public static ProductReview of(long productReviewNo, long productNo, long memberNo, String title, String content, StarRatingType starRatingType) {
        return ProductReview.builder()
                .productReviewNo(productReviewNo)
                .productNo(productNo)
                .memberNo(memberNo)
                .title(title)
                .content(content)
                .starRating(starRatingType.getStarRating())
                .isDeleted(false)
                .build();
    }

    public void delete() {
        this.isDeleted = true;
    }
}