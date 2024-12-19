package com.catalog_service.application.port.in;

import com.catalog_service.domain.ProductOutPut;

import java.util.List;

public interface RequestProductUseCase {

    List<ProductOutPut> selectProductListByProductNoList(SelectProductListRequestCommand request);
}
