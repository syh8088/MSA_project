package kiwi.shop.order.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentConfirmCommand {

    private String paymentKey;
    private String orderId;
    private String amount;

    @Builder
    private PaymentConfirmCommand(String paymentKey, String orderId, String amount) {
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.amount = amount;
    }

    public static PaymentConfirmCommand of(String paymentKey, String orderId, String amount) {
        return new PaymentConfirmCommand(paymentKey, orderId, amount);
    }
}
