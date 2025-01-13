package kiwi.shop.order.adapter.in.web.response;

import kiwi.shop.common.event.domain.PaymentOrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class PaymentOrderResponse {

    private long paymentOrderNo;
    private long productNo;
    private BigDecimal amount;
    private PaymentOrderStatus status;
    private String productName;

    @Builder
    private PaymentOrderResponse(long paymentOrderNo, long productNo, BigDecimal amount, PaymentOrderStatus status, String productName) {
        this.paymentOrderNo = paymentOrderNo;
        this.productNo = productNo;
        this.amount = amount;
        this.status = status;
        this.productName = productName;
    }

    public static PaymentOrderResponse of(long paymentOrderNo, long productNo, BigDecimal amount, PaymentOrderStatus status, String productName) {
        return PaymentOrderResponse.builder()
                .paymentOrderNo(paymentOrderNo)
                .productNo(productNo)
                .amount(amount)
                .status(status)
                .productName(productName)
                .build();
    }
}
