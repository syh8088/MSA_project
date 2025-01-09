package kiwi.shop.order.adapter.in.web.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PaymentOrderWithSellerResponses {

    private List<PaymentOrderWithSellerResponse> paymentOrderList;

    @Builder
    private PaymentOrderWithSellerResponses(List<PaymentOrderWithSellerResponse> paymentOrderList) {
        this.paymentOrderList = paymentOrderList;
    }

    public static PaymentOrderWithSellerResponses of(List<PaymentOrderWithSellerResponse> paymentOrderList) {
        return new PaymentOrderWithSellerResponses(paymentOrderList);
    }
}
