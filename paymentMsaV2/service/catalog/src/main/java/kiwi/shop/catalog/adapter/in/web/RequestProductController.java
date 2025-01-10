package kiwi.shop.catalog.adapter.in.web;

import kiwi.shop.catalog.application.port.in.RequestProductUseCase;
import kiwi.shop.catalog.application.port.in.SelectProductListRequestCommand;
import kiwi.shop.catalog.domain.ProductOutPut;
import kiwi.shop.catalog.domain.SelectProductResponse;
import kiwi.shop.catalog.domain.SelectProductResponses;
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
            @RequestParam(value = "productNoList") long[] productNoList
    ) {

        SelectProductListRequestCommand request = SelectProductListRequestCommand.of(productNoList);
        List<ProductOutPut> productList = requestProductUseCase.selectProductListByProductNoList(request);

        List<SelectProductResponse> selectProductResponses = SelectProductResponse.of(productList);
        SelectProductResponses result = SelectProductResponses.of(selectProductResponses);

        return ResponseEntity.ok(result);
    }
}
