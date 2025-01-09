package kiwi.shop.order.adapter.in.web;

import kiwi.shop.order.adapter.in.web.request.PaymentConfirmRequest;
import kiwi.shop.order.application.port.in.RequestOrderConfirmUseCase;
import kiwi.shop.order.domain.PaymentConfirmCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentConfirmController {

    private final RequestOrderConfirmUseCase requestOrderConfirmUseCase;

    @PostMapping("confirm")
    public ResponseEntity<?> paymentConfirm(@RequestBody PaymentConfirmRequest request) {

        PaymentConfirmCommand paymentConfirmCommand
                = PaymentConfirmCommand.of(request.getPaymentKey(), request.getOrderId(), request.getAmount());
        requestOrderConfirmUseCase.paymentConfirm(paymentConfirmCommand);

        return ResponseEntity.noContent().build();
    }

}
