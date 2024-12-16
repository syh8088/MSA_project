package com.catalog_service.domain;

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

    public static SelectProductResponse of(ProductOutPut product) {
        return SelectProductResponse.builder()
                .productNo(product.getProductNo())
                .sellerNo(product.getSellerNo())
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    public static List<SelectProductResponse> of(List<ProductOutPut> productList) {
        return productList.stream()
                .map(SelectProductResponse::of)
                .toList();
    }
}
