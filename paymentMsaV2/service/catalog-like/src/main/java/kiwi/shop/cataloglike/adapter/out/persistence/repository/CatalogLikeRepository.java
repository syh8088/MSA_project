package kiwi.shop.cataloglike.adapter.out.persistence.repository;

import kiwi.shop.cataloglike.adapter.out.persistence.entity.CatalogLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatalogLikeRepository extends JpaRepository<CatalogLike, Long> {

    @Query("select c from CatalogLike AS c where c.productNo = :productNo  and c.memberNo = :memberNo")
    Optional<CatalogLike> selectCatalogLikeByProductNoAndMemberNo(@Param("productNo") long productNo,  @Param("memberNo") long memberNo);
}
