package kiwi.shop.catalog.application.port.in;

import kiwi.shop.catalog.domain.ProductOutPut;

import java.util.List;

public interface RequestProductUseCase {

    List<ProductOutPut> selectProductListByProductNoList(SelectProductListRequestCommand request);
}
