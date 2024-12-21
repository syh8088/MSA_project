package com.order_service.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class PaymentOrderWithSellerOutPut {

    private long paymentOrderNo;
    private String orderId;
    private BigDecimal amount;
    private PaymentOrderStatus status;
    private long sellerNo;
    private String sellerId;
    private long productNo;

    @QueryProjection
    public PaymentOrderWithSellerOutPut(long paymentOrderNo, String orderId, BigDecimal amount, PaymentOrderStatus status, long sellerNo, String sellerId) {
        this.paymentOrderNo = paymentOrderNo;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
        this.sellerNo = sellerNo;
        this.sellerId = sellerId;
    }

    @QueryProjection
    public PaymentOrderWithSellerOutPut(long paymentOrderNo, String orderId, BigDecimal amount, PaymentOrderStatus status, long sellerNo, long productNo) {
        this.paymentOrderNo = paymentOrderNo;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
        this.sellerNo = sellerNo;
        this.productNo = productNo;
    }
}
