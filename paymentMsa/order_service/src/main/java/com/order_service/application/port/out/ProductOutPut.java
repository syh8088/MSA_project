package com.order_service.application.port.out;

import com.order_service.domain.SelectProductResponse;
import com.order_service.domain.SelectProductResponses;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class ProductOutPut {

    private long productNo;
    private long sellerNo;
    private String productId;
    private String name;
    private BigDecimal price;

    @QueryProjection
    public ProductOutPut(long productNo, long sellerNo, String productId, String name, BigDecimal price) {
        this.productNo = productNo;
        this.sellerNo = sellerNo;
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public static String getOrderName(List<ProductOutPut> productList) {

        if (!Objects.isNull(productList) && !productList.isEmpty()) {
            return productList.get(0).getName() + " 그외";
        }
        else {
            return productList.get(0).getName();
        }
    }

    public static ProductOutPut of(SelectProductResponse selectProductResponse) {
        return new ProductOutPut(
                selectProductResponse.getProductNo(),
                selectProductResponse.getSellerNo(),
                selectProductResponse.getProductId(),
                selectProductResponse.getName(),
                selectProductResponse.getPrice()
        );
    }

    public static List<ProductOutPut> of(SelectProductResponses selectProductResponses) {
        return selectProductResponses.getProductList().stream()
                .map(ProductOutPut::of)
                .toList();
    }
}
