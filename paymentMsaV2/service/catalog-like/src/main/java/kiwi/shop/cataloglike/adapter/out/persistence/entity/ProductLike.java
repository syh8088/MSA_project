package kiwi.shop.cataloglike.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_likes")
public class ProductLike extends CommonEntity {

    @Id
    @Column(name = "product_like_no")
    private Long productLikeNo;

    @Column(name = "product_no")
    private long productNo;

    @Column(name = "member_no")
    private long memberNo;
}
