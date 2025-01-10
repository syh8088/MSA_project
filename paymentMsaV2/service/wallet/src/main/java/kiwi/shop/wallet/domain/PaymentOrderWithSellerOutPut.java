package kiwi.shop.wallet.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class PaymentOrderWithSellerOutPut {

    private long paymentOrderNo;
    private String orderId;
    private BigDecimal amount;
    private PaymentOrderStatus status;
    private long sellerNo;
    private String sellerId;

    @Builder
    private PaymentOrderWithSellerOutPut(long paymentOrderNo, String orderId, BigDecimal amount, PaymentOrderStatus status, long sellerNo, String sellerId) {
        this.paymentOrderNo = paymentOrderNo;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
        this.sellerNo = sellerNo;
        this.sellerId = sellerId;
    }

    public static PaymentOrderWithSellerOutPut of(PaymentOrderWithSellerResponse paymentOrder) {
        return PaymentOrderWithSellerOutPut.builder()
                .paymentOrderNo(paymentOrder.getPaymentOrderNo())
                .orderId(paymentOrder.getOrderId())
                .amount(paymentOrder.getAmount())
                .status(paymentOrder.getStatus())
                .sellerNo(paymentOrder.getSellerNo())
                .sellerId(paymentOrder.getSellerId())
                .build();
    }

    public static List<PaymentOrderWithSellerOutPut> of(List<PaymentOrderWithSellerResponse> paymentOrderWithSellerResponse) {
        return paymentOrderWithSellerResponse.stream()
                .map(PaymentOrderWithSellerOutPut::of)
                .toList();
    }
}
