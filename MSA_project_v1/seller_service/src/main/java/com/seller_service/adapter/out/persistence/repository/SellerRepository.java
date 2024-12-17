package com.seller_service.adapter.out.persistence.repository;


import com.seller_service.adapter.out.persistence.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

}