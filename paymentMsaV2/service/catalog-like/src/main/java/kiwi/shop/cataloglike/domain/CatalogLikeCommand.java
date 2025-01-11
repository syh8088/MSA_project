package kiwi.shop.cataloglike.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CatalogLikeCommand {

    private long catalogLikeNo;
    private long productNo;
    private long memberNo;
    private LocalDateTime createdAt;

    @Builder
    private CatalogLikeCommand(long catalogLikeNo, long productNo, long memberNo, LocalDateTime createdAt) {
        this.catalogLikeNo = catalogLikeNo;
        this.productNo = productNo;
        this.memberNo = memberNo;
        this.createdAt = createdAt;
    }

    public static CatalogLikeCommand of(long catalogLikeNo, long productNo, long memberNo, LocalDateTime createdAt) {
        return CatalogLikeCommand.builder()
                .catalogLikeNo(catalogLikeNo)
                .productNo(productNo)
                .memberNo(memberNo)
                .createdAt(createdAt)
                .build();
    }
}
