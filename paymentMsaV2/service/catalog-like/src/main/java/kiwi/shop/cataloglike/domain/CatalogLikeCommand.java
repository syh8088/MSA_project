package kiwi.shop.cataloglike.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CatalogLikeCommand {

    private long catalogLikeNo;
    private long productNo;
    private long memberNo;

    @Builder
    private CatalogLikeCommand(long catalogLikeNo, long productNo, long memberNo) {
        this.catalogLikeNo = catalogLikeNo;
        this.productNo = productNo;
        this.memberNo = memberNo;
    }

    public static CatalogLikeCommand of(long catalogLikeNo, long productNo, long memberNo) {
        return CatalogLikeCommand.builder()
                .catalogLikeNo(catalogLikeNo)
                .productNo(productNo)
                .memberNo(memberNo)
                .build();
    }
}
