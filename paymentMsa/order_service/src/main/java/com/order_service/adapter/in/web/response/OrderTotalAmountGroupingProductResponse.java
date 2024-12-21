package com.order_service.adapter.in.web.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderTotalAmountGroupingProductResponse {

    private Double score;
    private String value;

    @Builder
    private OrderTotalAmountGroupingProductResponse(Double score, String value) {
        this.score = score;
        this.value = value;
    }

    public static OrderTotalAmountGroupingProductResponse of(Double score, String value) {
        return OrderTotalAmountGroupingProductResponse.builder().score(score).value(value).build();
    }
}
