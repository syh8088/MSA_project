package com.order_query_service.adapter.in.web.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class OrderTotalAmountGroupingProduct {

    private BigDecimal amount;
    private long productNo;

    @Builder
    private OrderTotalAmountGroupingProduct(BigDecimal amount, long productNo) {
        this.amount = amount;
        this.productNo = productNo;
    }

    public static OrderTotalAmountGroupingProduct of(Double amount, String productNo) {

        return OrderTotalAmountGroupingProduct.builder()
                .amount(BigDecimal.valueOf(amount))
                .productNo(Integer.parseInt(productNo))
                .build();
    }

    public static double calculateTotalAmount(List<OrderTotalAmountGroupingProduct> orderTotalAmountGroupingProductList) {

        if (Objects.isNull(orderTotalAmountGroupingProductList)) {
            return 0;
        }

        return orderTotalAmountGroupingProductList.stream()
                .mapToDouble(data -> data.getAmount().doubleValue())
                .sum();
    }
}
