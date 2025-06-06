package com.catalog_service.adapter.out.persistence.repository;

import com.catalog_service.adapter.out.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

//    @Query("select p from Product as p where p.no in :productNoList")
//    List<Product> selectProductListByProductNoList(@Param("productNoList") List<Long> productNoList);

    @Query("select p from Product as p")
    List<Product> selectProductAll();
}