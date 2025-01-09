package kiwi.shop.order.adapter.out.service;

import kiwi.shop.order.client.CatalogClient;
import kiwi.shop.order.application.port.out.GetCatalogPort;
import kiwi.shop.order.application.port.out.ProductOutPut;
import kiwi.shop.order.common.WebAdapter;
import kiwi.shop.order.domain.SelectProductListRequest;
import kiwi.shop.order.domain.SelectProductResponses;
import lombok.RequiredArgsConstructor;

import java.util.List;

@WebAdapter
@RequiredArgsConstructor
public class CatalogServiceAdapter implements GetCatalogPort {

    private final CatalogClient catalogClient;

    @Override
    public List<ProductOutPut> selectProductListByProductNoList(List<Long> productNoList) {

        SelectProductListRequest selectProductListRequest = SelectProductListRequest.of(productNoList);
        SelectProductResponses selectProductResponses = catalogClient.selectProducts(selectProductListRequest.getProductNoList());
        return ProductOutPut.of(selectProductResponses);
    }
}
