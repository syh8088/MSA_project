package kiwi.shop.catalog.adapter.in.web;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SelectProductListRequest {

    private List<Long> productNoList;

    @Builder
    private SelectProductListRequest(List<Long> productNoList) {
        this.productNoList = productNoList;
    }

    public static SelectProductListRequest of(List<Long> productNoList) {
        return SelectProductListRequest.builder()
                .productNoList(productNoList)
                .build();
    }
}
