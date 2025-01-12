package kiwi.shop.order.adapter.in.web;

import kiwi.shop.order.adapter.in.web.request.PaymentEventListRequest;
import kiwi.shop.order.adapter.in.web.response.PaymentEventResponse;
import kiwi.shop.order.adapter.in.web.response.PaymentEventResponses;
import kiwi.shop.order.adapter.in.web.response.PaymentOrderWithSellerResponse;
import kiwi.shop.order.adapter.in.web.response.PaymentOrderWithSellerResponses;
import kiwi.shop.order.application.port.in.RequestOrderUseCase;
import kiwi.shop.order.domain.PaymentEventWithOrderListCommand;
import kiwi.shop.order.domain.PaymentEventWithOrderOutPut;
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

    @GetMapping("/members/{memberNo}")
    public ResponseEntity<PaymentEventResponses> selectPaymentOrderList(
            @PathVariable("memberNo") long memberNo,
            @ModelAttribute PaymentEventListRequest paymentEventListRequest
    ) {

        PaymentEventWithOrderListCommand paymentEventWithOrderListCommand
                = PaymentEventWithOrderListCommand.of(
                        paymentEventListRequest.getPaymentEventNo(),
                        paymentEventListRequest.getCreatedDateTime(),
                        memberNo,
                        paymentEventListRequest.getLimit()
                );

        List<PaymentEventWithOrderOutPut> paymentEventWithOrderList
                = requestOrderUseCase.selectPaymentEventWithOrderListByMemberNo(paymentEventWithOrderListCommand);

        List<PaymentEventResponse> paymentEventResponseList = PaymentEventResponse.ofList(paymentEventWithOrderList);

        return ResponseEntity.ok().body(PaymentEventResponses.of(paymentEventResponseList));
    }

    @GetMapping("/payment-events/{paymentEventNo}/members/{memberNo}")
    public ResponseEntity<PaymentEventResponse> selectPaymentOrder(
            @PathVariable("paymentEventNo") long paymentEventNo,
            @PathVariable("memberNo") long memberNo
    ) {

        List<PaymentEventWithOrderOutPut> paymentEventWithOrderList
                = requestOrderUseCase.selectPaymentEventWithOrderListByMemberNoAndPaymentEventNo(memberNo, paymentEventNo);

        PaymentEventResponse paymentEventResponse = PaymentEventResponse.of(paymentEventWithOrderList);

        return ResponseEntity.ok().body(paymentEventResponse);
    }

}
