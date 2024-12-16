package com.order_service.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class SelectProductResponse {

    private long productNo;
    private long sellerNo;
    private String productId;
    private String name;
    private BigDecimal price;

    @Builder
    private SelectProductResponse(long productNo, long sellerNo, String productId, String name, BigDecimal price) {
        this.productNo = productNo;
        this.sellerNo = sellerNo;
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

}
