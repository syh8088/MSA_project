package com.order_service.application.port.out;

import java.util.List;

public interface GetCatalogtPort {

    List<ProductOutPut> selectProductListByProductNoList(List<Long> productNoList);
}
