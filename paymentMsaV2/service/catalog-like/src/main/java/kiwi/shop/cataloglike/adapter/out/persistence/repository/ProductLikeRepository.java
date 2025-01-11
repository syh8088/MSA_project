package kiwi.shop.cataloglike.adapter.out.persistence.repository;

import kiwi.shop.cataloglike.adapter.out.persistence.entity.ProductLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

    @Query("select c from ProductLike AS c where c.productNo = :productNo  and c.memberNo = :memberNo")
    Optional<ProductLike> selectProductLikeByProductNoAndMemberNo(@Param("productNo") long productNo, @Param("memberNo") long memberNo);
}
