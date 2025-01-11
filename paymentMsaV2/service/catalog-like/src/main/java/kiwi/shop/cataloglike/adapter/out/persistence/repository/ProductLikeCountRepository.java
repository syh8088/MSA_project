package kiwi.shop.cataloglike.adapter.out.persistence.repository;

import kiwi.shop.cataloglike.adapter.out.persistence.entity.ProductLikeCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLikeCountRepository extends JpaRepository<ProductLikeCount, Long> {

}
