package kiwi.shop.order.adapter.in.web;

import kiwi.shop.order.adapter.in.web.response.PaymentOrderWithSellerResponse;
import kiwi.shop.order.adapter.in.web.response.PaymentOrderWithSellerResponses;
import kiwi.shop.order.application.port.in.RequestOrderUseCase;
import kiwi.shop.order.domain.PaymentOrderWithSellerOutPut;
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

}
