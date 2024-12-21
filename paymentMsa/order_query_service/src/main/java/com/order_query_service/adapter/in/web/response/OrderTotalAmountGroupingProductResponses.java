package com.order_query_service.adapter.in.web.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderTotalAmountGroupingProductResponses {

    private List<OrderTotalAmountGroupingProductResponse> orderTotalAmountGroupingProductList;

    @Builder
    private OrderTotalAmountGroupingProductResponses(List<OrderTotalAmountGroupingProductResponse> orderTotalAmountGroupingProductList) {
        this.orderTotalAmountGroupingProductList = orderTotalAmountGroupingProductList;
    }

    public static OrderTotalAmountGroupingProductResponses of(List<OrderTotalAmountGroupingProductResponse> orderTotalAmountGroupingProductList) {
        return new OrderTotalAmountGroupingProductResponses(orderTotalAmountGroupingProductList);
    }
}
