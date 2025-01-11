package kiwi.shop.catalogreview.domain;

import kiwi.shop.catalogreview.adapter.in.web.request.StarRatingType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InsertCatalogReviewCommand {

    private long productNo;
    private long memberNo;
    private String title;
    private String content;
    private StarRatingType starRatingType;

    @Builder
    private InsertCatalogReviewCommand(long productNo, long memberNo, String title, String content, StarRatingType starRatingType) {
        this.productNo = productNo;
        this.memberNo = memberNo;
        this.title = title;
        this.content = content;
        this.starRatingType = starRatingType;
    }

    public static InsertCatalogReviewCommand of(long productNo, long memberNo, String title, String content, StarRatingType starRatingType) {
        return InsertCatalogReviewCommand.builder()
                .productNo(productNo)
                .memberNo(memberNo)
                .title(title)
                .content(content)
                .starRatingType(starRatingType)
                .build();
    }
}
