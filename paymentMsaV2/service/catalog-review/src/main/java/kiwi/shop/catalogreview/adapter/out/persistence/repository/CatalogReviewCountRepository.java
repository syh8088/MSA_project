package kiwi.shop.catalogreview.adapter.out.persistence.repository;

import kiwi.shop.catalogreview.adapter.out.persistence.entity.CatalogReviewCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogReviewCountRepository extends JpaRepository<CatalogReviewCount, Long>, CatalogReviewCountRepositoryCustom {
}
