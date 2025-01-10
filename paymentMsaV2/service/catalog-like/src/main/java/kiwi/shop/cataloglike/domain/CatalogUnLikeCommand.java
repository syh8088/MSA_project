package kiwi.shop.cataloglike.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CatalogUnLikeCommand {

    private long productNo;
    private long memberNo;

    @Builder
    private CatalogUnLikeCommand(long productNo, long memberNo) {
        this.productNo = productNo;
        this.memberNo = memberNo;
    }

    public static CatalogUnLikeCommand of(long productNo, long memberNo) {
        return CatalogUnLikeCommand.builder()
                .productNo(productNo)
                .memberNo(memberNo)
                .build();
    }
}
