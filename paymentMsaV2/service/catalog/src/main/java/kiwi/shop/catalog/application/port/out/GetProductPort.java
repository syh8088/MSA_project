package kiwi.shop.catalog.application.port.out;


import kiwi.shop.catalog.application.port.in.SelectProductListRequestCommand;
import kiwi.shop.catalog.domain.ProductOutPut;

import java.util.List;

public interface GetProductPort {

    List<ProductOutPut> selectProductListByProductNoList(SelectProductListRequestCommand command);
}
