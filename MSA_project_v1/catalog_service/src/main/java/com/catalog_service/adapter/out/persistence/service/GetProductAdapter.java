package com.catalog_service.adapter.out.persistence.service;

import com.catalog_service.adapter.out.persistence.repository.ProductRepository;
import com.catalog_service.application.port.in.SelectProductListRequestCommand;
import com.catalog_service.application.port.out.GetProductPort;
import com.catalog_service.domain.ProductOutPut;
import com.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class GetProductAdapter implements GetProductPort {

    private final ProductRepository productRepository;


    @Override
    public List<ProductOutPut> selectProductListByProductNoList(SelectProductListRequestCommand command) {

        return productRepository.selectProductListByProductNoList(command.getProductNoList());
    }
}
