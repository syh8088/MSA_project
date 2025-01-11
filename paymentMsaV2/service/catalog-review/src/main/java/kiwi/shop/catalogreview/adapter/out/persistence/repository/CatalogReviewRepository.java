package kiwi.shop.catalogreview.adapter.out.persistence.repository;

import kiwi.shop.catalogreview.adapter.out.persistence.entity.CatalogReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CatalogReviewRepository extends JpaRepository<CatalogReview, Long> {

    @Modifying
    @Query("UPDATE CatalogReview AS c SET c.isDeleted = :isDeleted, c.updatedDateTime = CURRENT_TIMESTAMP WHERE c.catalogReviewNo = :catalogReviewNo")
    void deleteByCatalogReviewNo(@Param("catalogReviewNo") long catalogReviewNo);
}
