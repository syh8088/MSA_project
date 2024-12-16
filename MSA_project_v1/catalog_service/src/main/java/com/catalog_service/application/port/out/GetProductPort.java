package com.catalog_service.application.port.out;


import com.catalog_service.application.port.in.SelectProductListRequestCommand;
import com.catalog_service.domain.ProductOutPut;

import java.util.List;

public interface GetProductPort {

    List<ProductOutPut> selectProductListByProductNoList(SelectProductListRequestCommand command);
}
