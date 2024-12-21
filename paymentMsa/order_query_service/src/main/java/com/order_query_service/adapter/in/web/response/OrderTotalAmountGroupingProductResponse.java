package com.order_query_service.adapter.in.web.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderTotalAmountGroupingProductResponse {

    private BigDecimal amount;
    private long productNo;

    @Builder
    private OrderTotalAmountGroupingProductResponse(BigDecimal amount, long productNo) {
        this.amount = amount;
        this.productNo = productNo;
    }

    public static List<OrderTotalAmountGroupingProductResponse> of(List<OrderTotalAmountGroupingProduct> orderTotalAmountGroupingProductList) {

        return orderTotalAmountGroupingProductList.stream()
                .map(OrderTotalAmountGroupingProductResponse::of)
                .toList();
    }

    public static OrderTotalAmountGroupingProductResponse of(OrderTotalAmountGroupingProduct orderTotalAmountGroupingProduct) {

        return OrderTotalAmountGroupingProductResponse.builder()
                .amount(orderTotalAmountGroupingProduct.getAmount())
                .productNo(orderTotalAmountGroupingProduct.getProductNo())
                .build();
    }
}
