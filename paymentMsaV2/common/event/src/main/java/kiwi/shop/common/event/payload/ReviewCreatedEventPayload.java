package kiwi.shop.common.event.payload;

import kiwi.shop.common.event.EventPayload;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreatedEventPayload implements EventPayload {

    private long catalogReviewNo;
    private long productNo;
    private long memberNo;
    private String title;
    private String content;
    private long starRating;
    private Boolean isDeleted;
    private long reviewCount;
    private long reviewStarRatingSum;

    @Builder
    private ReviewCreatedEventPayload(long catalogReviewNo, long productNo, long memberNo, String title, String content, long starRating, Boolean isDeleted, long reviewCount, long reviewStarRatingSum) {
        this.catalogReviewNo = catalogReviewNo;
        this.productNo = productNo;
        this.memberNo = memberNo;
        this.title = title;
        this.content = content;
        this.starRating = starRating;
        this.isDeleted = isDeleted;
        this.reviewCount = reviewCount;
        this.reviewStarRatingSum = reviewStarRatingSum;
    }

    public static ReviewCreatedEventPayload of(long catalogReviewNo, long productNo, long memberNo, String title, String content, long starRating, Boolean isDeleted, long reviewCount, long reviewStarRatingSum) {
        return ReviewCreatedEventPayload.builder()
                .catalogReviewNo(catalogReviewNo)
                .productNo(productNo)
                .memberNo(memberNo)
                .title(title)
                .content(content)
                .starRating(starRating)
                .isDeleted(isDeleted)
                .reviewCount(reviewCount)
                .reviewStarRatingSum(reviewStarRatingSum)
                .build();
    }
}
