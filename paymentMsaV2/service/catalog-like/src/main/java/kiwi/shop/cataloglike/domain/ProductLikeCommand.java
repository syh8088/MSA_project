package kiwi.shop.cataloglike.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductLikeCommand {

    private long productLikeNo;
    private long productNo;
    private long memberNo;
    private LocalDateTime createdAt;

    @Builder
    private ProductLikeCommand(long productLikeNo, long productNo, long memberNo, LocalDateTime createdAt) {
        this.productLikeNo = productLikeNo;
        this.productNo = productNo;
        this.memberNo = memberNo;
        this.createdAt = createdAt;
    }

    public static ProductLikeCommand of(long catalogLikeNo, long productNo, long memberNo, LocalDateTime createdAt) {
        return ProductLikeCommand.builder()
                .productLikeNo(catalogLikeNo)
                .productNo(productNo)
                .memberNo(memberNo)
                .createdAt(createdAt)
                .build();
    }
}
