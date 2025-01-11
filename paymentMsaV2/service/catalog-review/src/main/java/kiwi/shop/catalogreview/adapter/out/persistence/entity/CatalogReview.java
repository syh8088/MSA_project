package kiwi.shop.catalogreview.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kiwi.shop.catalogreview.adapter.in.web.request.StarRatingType;
import kiwi.shop.common.outboxmessagerelay.CommonEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "catalog_reviews")
public class CatalogReview extends CommonEntity {

    @Id
    @Column(name = "catalog_review_no")
    private Long catalogLikeNo;

    @Column(name = "product_no")
    private long productNo;

    @Column(name = "member_no")
    private long memberNo;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "star_rating")
    private BigDecimal starRating;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Builder
    private CatalogReview(Long catalogLikeNo, long productNo, long memberNo, String title, String content, BigDecimal starRating, Boolean isDeleted) {
        this.catalogLikeNo = catalogLikeNo;
        this.productNo = productNo;
        this.memberNo = memberNo;
        this.title = title;
        this.content = content;
        this.starRating = starRating;
        this.isDeleted = isDeleted;
    }

    public static CatalogReview of(long catalogLikeNo, long productNo, long memberNo, String title, String content, StarRatingType starRatingType) {
        return CatalogReview.builder()
                .catalogLikeNo(catalogLikeNo)
                .productNo(productNo)
                .memberNo(memberNo)
                .title(title)
                .content(content)
                .starRating(starRatingType.getStarRating())
                .isDeleted(false)
                .build();
    }
}