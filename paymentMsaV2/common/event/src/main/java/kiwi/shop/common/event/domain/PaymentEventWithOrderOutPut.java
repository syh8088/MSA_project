package kiwi.shop.common.event.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

@Getter
@NoArgsConstructor
public class PaymentEventWithOrderOutPut {

    private long paymentEventNo;
    private long memberNo;
    private String orderId;
    private String paymentKey;
    private String orderName;
    private PaymentEventMethod method;
    private PaymentEventType type;
    private LocalDateTime approvedDateTime;
    private boolean isPaymentDone;

    private long paymentOrderNo;
    private long productNo;
    private long sellerNo;

    private BigDecimal amount;
    private PaymentOrderStatus status;
    private String productName;

    @QueryProjection
    public PaymentEventWithOrderOutPut(long paymentEventNo, long memberNo, String orderId, String paymentKey, String orderName, PaymentEventMethod method, PaymentEventType type, LocalDateTime approvedDateTime, boolean isPaymentDone, long paymentOrderNo, long productNo, long sellerNo, BigDecimal amount, PaymentOrderStatus status, String productName) {
        this.paymentEventNo = paymentEventNo;
        this.memberNo = memberNo;
        this.orderId = orderId;
        this.paymentKey = paymentKey;
        this.orderName = orderName;
        this.method = method;
        this.type = type;
        this.approvedDateTime = approvedDateTime;
        this.isPaymentDone = isPaymentDone;
        this.paymentOrderNo = paymentOrderNo;
        this.productNo = productNo;
        this.sellerNo = sellerNo;
        this.amount = amount;
        this.status = status;
        this.productName = productName;
    }
}
