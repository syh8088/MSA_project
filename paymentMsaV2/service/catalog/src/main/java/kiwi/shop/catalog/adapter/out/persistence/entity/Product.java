package kiwi.shop.catalog.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "products")
public class Product extends CommonEntity {

    @Id
    @Column(name = "product_no")
    private Long productNo;

    @Column(name = "seller_no")
    private long sellerNo;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Builder
    private Product(Long productNo, long sellerNo, String productId, String name, BigDecimal price) {
        this.productNo = productNo;
        this.sellerNo = sellerNo;
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public static Product of(String productId, String name, BigDecimal price) {
        return Product.builder()
                .productId(productId)
                .name(name)
                .price(price)
                .build();
    }

    public static Product of(long productNo) {
        return Product.builder()
                .productNo(productNo)
                .build();
    }

    public static List<Product> of(List<Long> productIdList) {
        return productIdList.stream()
                .map(Product::of)
                .collect(Collectors.toList());
    }
}
