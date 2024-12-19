package com.catalog_service.adapter.in.web;

import com.catalog_service.application.port.in.RequestProductUseCase;
import com.catalog_service.application.port.in.SelectProductListRequestCommand;
import com.catalog_service.domain.ProductOutPut;
import com.catalog_service.domain.SelectProductResponse;
import com.catalog_service.domain.SelectProductResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class RequestProductController {

    private final RequestProductUseCase requestProductUseCase;

    @GetMapping
    public ResponseEntity<SelectProductResponses> selectProductListByProductNoList(
            @RequestParam(value = "productNo") long[] productNoList
    ) {

        SelectProductListRequestCommand request = SelectProductListRequestCommand.of(productNoList);
        List<ProductOutPut> productList = requestProductUseCase.selectProductListByProductNoList(request);

        List<SelectProductResponse> selectProductResponses = SelectProductResponse.of(productList);
        SelectProductResponses result = SelectProductResponses.of(selectProductResponses);

        return ResponseEntity.ok(result);
    }
}
