package kiwi.shop.catalog.application.service;

import kiwi.shop.catalog.application.port.in.RequestProductUseCase;
import kiwi.shop.catalog.application.port.in.SelectProductListRequestCommand;
import kiwi.shop.catalog.application.port.out.GetProductPort;
import kiwi.shop.catalog.common.UseCase;
import kiwi.shop.catalog.domain.ProductOutPut;
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
