package com.order_query_service.adapter.in.web.response;

import com.order_query_service.domain.PaymentOrderStatus;
import com.order_query_service.domain.PaymentOrderWithSellerOutPut;
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
    private long productNo;

    @Builder
    private PaymentOrderWithSellerResponse(long paymentOrderNo, String orderId, BigDecimal amount, PaymentOrderStatus status, long sellerNo, String sellerId, long productNo) {
        this.paymentOrderNo = paymentOrderNo;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
        this.sellerNo = sellerNo;
        this.sellerId = sellerId;
        this.productNo = productNo;
    }

    public static PaymentOrderWithSellerResponse of(PaymentOrderWithSellerOutPut paymentOrderWithSellerOutPut) {
        return PaymentOrderWithSellerResponse.builder()
                .paymentOrderNo(paymentOrderWithSellerOutPut.getPaymentOrderNo())
                .orderId(paymentOrderWithSellerOutPut.getOrderId())
                .amount(paymentOrderWithSellerOutPut.getAmount())
                .status(paymentOrderWithSellerOutPut.getStatus())
                .sellerNo(paymentOrderWithSellerOutPut.getSellerNo())
                .sellerId(paymentOrderWithSellerOutPut.getSellerId())
                .productNo(paymentOrderWithSellerOutPut.getProductNo())
                .build();
    }

    public static List<PaymentOrderWithSellerResponse> of(List<PaymentOrderWithSellerOutPut> paymentOrderWithSellerOutPuts) {
        return paymentOrderWithSellerOutPuts.stream()
                .map(PaymentOrderWithSellerResponse::of)
                .toList();
    }
}
