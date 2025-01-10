package kiwi.shop.catalog.adapter.out.persistence.repository;


import kiwi.shop.catalog.domain.ProductOutPut;

import java.util.List;

public interface ProductRepositoryCustom {

    List<ProductOutPut> selectProductListByProductNoList(List<Long> productNoList);
}
