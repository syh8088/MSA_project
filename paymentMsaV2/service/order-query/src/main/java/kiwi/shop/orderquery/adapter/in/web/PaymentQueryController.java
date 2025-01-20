package kiwi.shop.orderquery.adapter.in.web;

import kiwi.shop.orderquery.adapter.in.web.request.PaymentEventListRequest;
import kiwi.shop.orderquery.application.port.in.PaymentQueryUseCase;
import kiwi.shop.orderquery.domain.PaymentEventResponse;
import kiwi.shop.orderquery.domain.PaymentEventResponses;
import kiwi.shop.orderquery.domain.PaymentEventWithOrderListCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class PaymentQueryController {

    private final PaymentQueryUseCase paymentQueryUseCase;

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

        List<PaymentEventResponse> paymentEventResponseList
                = paymentQueryUseCase.selectPaymentEventWithOrderList(paymentEventWithOrderListCommand);

        return ResponseEntity.ok().body(PaymentEventResponses.of(paymentEventResponseList));
    }

    @GetMapping("/payment-events/{paymentEventNo}/members/{memberNo}")
    public ResponseEntity<PaymentEventResponse> selectPaymentOrder(
            @PathVariable("paymentEventNo") long paymentEventNo,
            @PathVariable("memberNo") long memberNo
    ) {

        PaymentEventResponse paymentEventResponse
                = paymentQueryUseCase.selectPaymentOrderDetail(memberNo, paymentEventNo);

        return ResponseEntity.ok().body(paymentEventResponse);
    }

}
