package com.catalog_service.adapter.out.persistence.repository;


import com.catalog_service.domain.ProductOutPut;

import java.util.List;

public interface ProductRepositoryCustom {

    List<ProductOutPut> selectProductListByProductNoList(List<Long> productNoList);
}
