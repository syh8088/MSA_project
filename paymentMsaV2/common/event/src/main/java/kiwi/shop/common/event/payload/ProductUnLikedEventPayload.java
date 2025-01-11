package kiwi.shop.common.event.payload;

import kiwi.shop.common.event.EventPayload;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductUnLikedEventPayload implements EventPayload {

    private long catalogLikeNo;
    private long productNo;
    private long memberNo;
    private long productLikeCount;
    private LocalDateTime createdAt;

    @Builder
    private ProductUnLikedEventPayload(long catalogLikeNo, long productNo, long memberNo, long productLikeCount, LocalDateTime createdAt) {
        this.catalogLikeNo = catalogLikeNo;
        this.productNo = productNo;
        this.memberNo = memberNo;
        this.productLikeCount = productLikeCount;
        this.createdAt = createdAt;
    }

    public static ProductUnLikedEventPayload of(long catalogLikeNo, long productNo, long memberNo, long productLikeCount, LocalDateTime createdAt) {
        return ProductUnLikedEventPayload.builder()
                .catalogLikeNo(catalogLikeNo)
                .productNo(productNo)
                .memberNo(memberNo)
                .productLikeCount(productLikeCount)
                .createdAt(createdAt)
                .build();
    }
}
