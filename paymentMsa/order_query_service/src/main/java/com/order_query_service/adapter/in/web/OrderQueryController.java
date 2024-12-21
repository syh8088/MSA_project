package com.order_query_service.adapter.in.web;

import com.order_query_service.application.port.in.GetOrderQueryUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderQueryController {

    private final GetOrderQueryUseCase getOrderQueryUseCase;

    @GetMapping("/sellers/{sellerNo}")
    public ResponseEntity<?> selectOrderTotalAmountGroupingProductBySellerNo(@PathVariable(value = "sellerNo") long sellerNo) {

        getOrderQueryUseCase.selectOrderTotalAmountGroupingProductBySellerNo(sellerNo);

        return ResponseEntity.ok().body(null);
    }

}
