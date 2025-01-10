package kiwi.shop.common.event.payload;

import kiwi.shop.common.event.EventPayload;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentConfirmEventPayload implements EventPayload {

    private String orderId;

    @Builder
    private PaymentConfirmEventPayload(String orderId) {
        this.orderId = orderId;
    }

    public static PaymentConfirmEventPayload of(String orderId) {
        return PaymentConfirmEventPayload
                .builder()
                .orderId(orderId)
                .build();
    }
}
