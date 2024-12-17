package com.wallet_service.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class PaymentOrderWithSellerResponse {

    private long paymentOrderNo;
    private String orderId;
    private BigDecimal amount;
    private PaymentOrderStatus status;
    private long sellerNo;
    private String sellerId;

    @Builder
    private PaymentOrderWithSellerResponse(long paymentOrderNo, String orderId, BigDecimal amount, PaymentOrderStatus status, long sellerNo, String sellerId) {
        this.paymentOrderNo = paymentOrderNo;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
        this.sellerNo = sellerNo;
        this.sellerId = sellerId;
    }

    public static PaymentOrderWithSellerResponse of(PaymentOrderWithSellerOutPut paymentOrderWithSellerOutPut) {
        return PaymentOrderWithSellerResponse.builder()
                .paymentOrderNo(paymentOrderWithSellerOutPut.getPaymentOrderNo())
                .orderId(paymentOrderWithSellerOutPut.getOrderId())
                .amount(paymentOrderWithSellerOutPut.getAmount())
                .status(paymentOrderWithSellerOutPut.getStatus())
                .sellerNo(paymentOrderWithSellerOutPut.getSellerNo())
                .sellerId(paymentOrderWithSellerOutPut.getSellerId())
                .build();
    }

    public static List<PaymentOrderWithSellerResponse> of(List<PaymentOrderWithSellerOutPut> paymentOrderWithSellerOutPuts) {
        return paymentOrderWithSellerOutPuts.stream()
                .map(PaymentOrderWithSellerResponse::of)
                .toList();
    }
}
