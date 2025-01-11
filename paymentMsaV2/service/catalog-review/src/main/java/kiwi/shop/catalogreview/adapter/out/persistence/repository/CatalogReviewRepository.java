package kiwi.shop.catalogreview.adapter.out.persistence.repository;

import kiwi.shop.catalogreview.adapter.out.persistence.entity.CatalogReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogReviewRepository extends JpaRepository<CatalogReview, Long> {

}
