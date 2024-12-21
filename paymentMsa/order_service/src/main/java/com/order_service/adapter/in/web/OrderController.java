package com.order_service.adapter.in.web;

import com.order_service.adapter.in.web.response.PaymentOrderWithSellerResponse;
import com.order_service.adapter.in.web.response.PaymentOrderWithSellerResponses;
import com.order_service.application.port.in.RequestOrderUseCase;
import com.order_service.domain.PaymentOrderWithSellerOutPut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final RequestOrderUseCase requestOrderUseCase;

    @GetMapping
    public ResponseEntity<PaymentOrderWithSellerResponses> selectPaymentOrderListWithSellerByOrderId(@RequestParam(value = "orderId") String orderId) {

        List<PaymentOrderWithSellerOutPut> paymentOrderWithSellerList
                = requestOrderUseCase.selectPaymentOrderListWithSellerByOrderId(orderId);

        List<PaymentOrderWithSellerResponse> paymentOrderWithSellerResponses
                = PaymentOrderWithSellerResponse.of(paymentOrderWithSellerList);

        return ResponseEntity.ok().body(PaymentOrderWithSellerResponses.of(paymentOrderWithSellerResponses));
    }

    @GetMapping("/sellers/{sellerNo}")
    public ResponseEntity<?> selectOrderTotalAmountGroupingProductBySellerNo(@PathVariable(value = "sellerNo") long sellerNo) {

        requestOrderUseCase.selectOrderTotalAmountGroupingProductBySellerNo(sellerNo);


        return ResponseEntity.ok().body(null);
    }

}
