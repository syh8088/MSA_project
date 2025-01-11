package kiwi.shop.catalogreview.adapter.out.persistence.repository;

import kiwi.shop.catalogreview.adapter.out.persistence.entity.ProductReviewCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReviewCountRepository extends JpaRepository<ProductReviewCount, Long>, ProductReviewCountRepositoryCustom {
}
