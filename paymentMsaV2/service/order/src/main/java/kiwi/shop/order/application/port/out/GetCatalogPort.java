package kiwi.shop.order.application.port.out;

import java.util.List;

public interface GetCatalogPort {

    List<ProductOutPut> selectProductListByProductNoList(List<Long> productNoList);
}
