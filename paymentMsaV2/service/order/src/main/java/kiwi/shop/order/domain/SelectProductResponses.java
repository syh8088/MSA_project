package kiwi.shop.order.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SelectProductResponses {

    private List<SelectProductResponse> productList;

    @Builder
    private SelectProductResponses(List<SelectProductResponse> productList) {
        this.productList = productList;
    }

    public static SelectProductResponses of(List<SelectProductResponse> productList) {
        return SelectProductResponses.builder()
                .productList(productList)
                .build();
    }
}
