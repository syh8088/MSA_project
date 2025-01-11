package kiwi.shop.cataloglike.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_like_counts")
public class CatalogLikeCount extends CommonEntity {

    @Id
    @Column(name = "product_no")
    private Long productNo;

    @Column(name = "like_count")
    private Long likeCount;

    @Version
    @Column(name = "version")
    private Long version;

    @Builder
    private CatalogLikeCount(Long productNo, Long likeCount) {
        this.productNo = productNo;
        this.likeCount = likeCount;
    }

    public static CatalogLikeCount of(long productNo, long likeCount) {
        return CatalogLikeCount.builder()
                .productNo(productNo)
                .likeCount(likeCount)
                .build();
    }

    public void increase() {
        this.likeCount++;
    }

    public void decrease() {
        this.likeCount--;
    }
}
