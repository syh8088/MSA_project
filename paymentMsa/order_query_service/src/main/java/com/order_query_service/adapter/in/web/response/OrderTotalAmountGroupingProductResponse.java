package com.order_query_service.adapter.in.web.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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

    public static OrderTotalAmountGroupingProductResponse of(Double amount, String productNo) {

        return OrderTotalAmountGroupingProductResponse.builder()
                .amount(BigDecimal.valueOf(amount))
                .productNo(Integer.parseInt(productNo))
                .build();
    }

    public static double calculateTotalAmount(List<OrderTotalAmountGroupingProductResponse> orderTotalAmountGroupingProductResponseList) {

        if (Objects.isNull(orderTotalAmountGroupingProductResponseList)) {
            return 0;
        }

        return orderTotalAmountGroupingProductResponseList.stream()
                .mapToDouble(data -> data.getAmount().doubleValue())
                .sum();
    }
}
