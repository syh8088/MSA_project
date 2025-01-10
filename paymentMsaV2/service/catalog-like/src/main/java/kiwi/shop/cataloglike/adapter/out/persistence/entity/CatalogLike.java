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
@Table(name = "catalog_likes")
public class CatalogLike extends CommonEntity {

    @Id
    @Column(name = "catalog_like_no")
    private Long catalogLikeNo;

    @Column(name = "product_no")
    private long productNo;

    @Column(name = "member_no")
    private long memberNo;
}
