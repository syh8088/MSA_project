package kiwi.shop.orderquery.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PaymentEventResponses {

    private List<PaymentEventResponse> paymentEventList;

    @Builder
    private PaymentEventResponses(List<PaymentEventResponse> paymentEventList) {
        this.paymentEventList = paymentEventList;
    }

    public static PaymentEventResponses of(List<PaymentEventResponse> paymentEventList) {
        return PaymentEventResponses.builder()
                .paymentEventList(paymentEventList)
                .build();
    }
}
