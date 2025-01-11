package kiwi.shop.cataloglike.adapter.out.persistence.repository;

import kiwi.shop.cataloglike.adapter.out.persistence.entity.CatalogLikeCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogLikeCountRepository extends JpaRepository<CatalogLikeCount, Long> {

}
