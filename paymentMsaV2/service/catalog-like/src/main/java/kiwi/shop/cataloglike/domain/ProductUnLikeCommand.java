package kiwi.shop.cataloglike.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductUnLikeCommand implements UpdateProductLikeCommand {

    private long productNo;
    private long memberNo;

    @Builder
    private ProductUnLikeCommand(long productNo, long memberNo) {
        this.productNo = productNo;
        this.memberNo = memberNo;
    }

    public static ProductUnLikeCommand of(long productNo, long memberNo) {
        return ProductUnLikeCommand.builder()
                .productNo(productNo)
                .memberNo(memberNo)
                .build();
    }
}
