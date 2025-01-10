package kiwi.shop.seller.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sellers")
public class Seller extends CommonEntity {

    @Id
    @Column(name = "seller_no")
    private Long sellerNo;

    @Column(name = "seller_id")
    private String sellerId;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Builder
    private Seller(Long sellerNo, String sellerId) {
        this.sellerNo = sellerNo;
        this.sellerId = sellerId;
    }

    public static Seller of(String sellerId) {
        return Seller.builder()
                .sellerId(sellerId)
                .build();
    }

    public static Seller of(long sellerNo) {
        return Seller.builder()
                .sellerNo(sellerNo)
                .build();
    }
}
