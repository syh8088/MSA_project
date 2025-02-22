package com.seller_service.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sellers")
public class Seller extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long no;

    @Column(name = "seller_id")
    private String sellerId;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Builder
    private Seller(Long no, String sellerId) {
        this.no = no;
        this.sellerId = sellerId;
    }

    public static Seller of(String sellerId) {
        return Seller.builder()
                .sellerId(sellerId)
                .build();
    }

    public static Seller of(long no) {
        return Seller.builder()
                .no(no)
                .build();
    }
}
