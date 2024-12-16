package com.order_service.application.port.out;

import java.util.List;

public interface GetProductPort {

    List<ProductOutPut> selectProductListByProductNoList(List<Long> productNoList);
}
