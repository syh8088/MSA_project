package kiwi.shop.catalog.adapter.out.persistence.service;

import kiwi.shop.catalog.adapter.out.persistence.repository.ProductRepository;
import kiwi.shop.catalog.application.port.in.SelectProductListRequestCommand;
import kiwi.shop.catalog.application.port.out.GetProductPort;
import kiwi.shop.catalog.common.WebAdapter;
import kiwi.shop.catalog.domain.ProductOutPut;

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
