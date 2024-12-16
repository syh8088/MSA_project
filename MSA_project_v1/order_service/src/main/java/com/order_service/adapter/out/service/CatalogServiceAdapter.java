package com.order_service.adapter.out.service;

import com.common.WebAdapter;
import com.order_service.client.CatalogClient;
import com.order_service.application.port.out.GetCatalogtPort;
import com.order_service.application.port.out.ProductOutPut;
import com.order_service.domain.SelectProductListRequest;
import com.order_service.domain.SelectProductResponses;
import lombok.RequiredArgsConstructor;

import java.util.List;

@WebAdapter
@RequiredArgsConstructor
public class CatalogServiceAdapter implements GetCatalogtPort {

    private final CatalogClient catalogClient;

    @Override
    public List<ProductOutPut> selectProductListByProductNoList(List<Long> productNoList) {

        SelectProductListRequest selectProductListRequest = SelectProductListRequest.of(productNoList);
        SelectProductResponses selectProductResponses = catalogClient.selectProducts(selectProductListRequest.getProductNoList());
        return ProductOutPut.of(selectProductResponses);
    }
}
