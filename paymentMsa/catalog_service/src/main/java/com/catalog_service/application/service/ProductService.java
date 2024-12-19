package com.catalog_service.application.service;

import com.catalog_service.application.port.in.RequestProductUseCase;
import com.catalog_service.application.port.in.SelectProductListRequestCommand;
import com.catalog_service.application.port.out.GetProductPort;
import com.catalog_service.domain.ProductOutPut;
import com.common.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@UseCase
@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class ProductService implements RequestProductUseCase {

    private final GetProductPort getProductPort;

    @Override
    public List<ProductOutPut> selectProductListByProductNoList(SelectProductListRequestCommand command) {

        return getProductPort.selectProductListByProductNoList(command);
    }
}
