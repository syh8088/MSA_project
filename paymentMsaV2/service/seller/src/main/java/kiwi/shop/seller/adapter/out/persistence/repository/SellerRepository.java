package kiwi.shop.seller.adapter.out.persistence.repository;

import kiwi.shop.seller.adapter.out.persistence.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Modifying
    @Query("UPDATE Seller AS s SET s.isDeleted = :isDeleted WHERE s.sellerNo = :sellerNo")
    void updateSellerIsDeletedBySellerNo(@Param("sellerNo") long sellerNo, @Param("isDeleted") boolean isDeleted);
}