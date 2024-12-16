package com.order_service.adapter.out.service;

import com.order_service.application.port.out.GetProductPort;
import com.order_service.application.port.out.ProductOutPut;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceAdapter implements GetProductPort {

    @Override
    public List<ProductOutPut> selectProductListByProductNoList(List<Long> productNoList) {
        return List.of();
    }
}
