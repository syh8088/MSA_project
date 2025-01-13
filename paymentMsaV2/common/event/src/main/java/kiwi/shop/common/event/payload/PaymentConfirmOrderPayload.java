package kiwi.shop.common.event.payload;

import kiwi.shop.common.event.domain.PaymentEventWithOrderOutPut;
import kiwi.shop.common.event.domain.PaymentOrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class PaymentConfirmOrderPayload {

    private long paymentOrderNo;
    private long productNo;
    private BigDecimal amount;
    private PaymentOrderStatus status;
    private String productName;

    @Builder
    private PaymentConfirmOrderPayload(long paymentOrderNo, long productNo, BigDecimal amount, PaymentOrderStatus status, String productName) {
        this.paymentOrderNo = paymentOrderNo;
        this.productNo = productNo;
        this.amount = amount;
        this.status = status;
        this.productName = productName;
    }

    public static List<PaymentConfirmOrderPayload> of(List<PaymentEventWithOrderOutPut> paymentEventWithOrderList) {
        return paymentEventWithOrderList.stream()
                .map(PaymentConfirmOrderPayload::of)
                .toList();
    }

    public static PaymentConfirmOrderPayload of(PaymentEventWithOrderOutPut paymentEventWithOrder) {
        return PaymentConfirmOrderPayload.builder()
                .paymentOrderNo(paymentEventWithOrder.getPaymentOrderNo())
                .productNo(paymentEventWithOrder.getProductNo())
                .amount(paymentEventWithOrder.getAmount())
                .status(paymentEventWithOrder.getStatus())
                .productName(paymentEventWithOrder.getProductName())
                .build();
    }
}
