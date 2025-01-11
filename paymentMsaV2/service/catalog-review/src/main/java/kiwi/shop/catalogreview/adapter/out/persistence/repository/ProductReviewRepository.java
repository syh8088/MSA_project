package kiwi.shop.catalogreview.adapter.out.persistence.repository;

import kiwi.shop.catalogreview.adapter.out.persistence.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    @Modifying
    @Query("UPDATE ProductReview AS c SET c.isDeleted = :isDeleted, c.updatedDateTime = CURRENT_TIMESTAMP WHERE c.productReviewNo = :catalogReviewNo")
    void deleteByProductReviewNo(@Param("catalogReviewNo") long catalogReviewNo);
}
