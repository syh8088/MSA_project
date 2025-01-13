package kiwi.shop.order.application.port.out;

import com.querydsl.core.annotations.QueryProjection;
import kiwi.shop.common.event.domain.PaymentEventWithOrderOutPut;
import kiwi.shop.common.event.domain.PaymentOrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class PaymentOrderStatusOutPut {

    private long paymentOrderNo;
    private String orderId;
    private PaymentOrderStatus status;
    private BigDecimal amount;

    @QueryProjection
    public PaymentOrderStatusOutPut(long paymentOrderNo, String orderId, PaymentOrderStatus status, BigDecimal amount) {
        this.paymentOrderNo = paymentOrderNo;
        this.orderId = orderId;
        this.status = status;
        this.amount = amount;
    }

    public static List<PaymentOrderStatusOutPut> of(List<PaymentEventWithOrderOutPut> paymentEventWithOrderList) {
        return paymentEventWithOrderList.stream()
                .map(data -> {
                    PaymentOrderStatusOutPut paymentOrderStatusOutPut = new PaymentOrderStatusOutPut();
                    paymentOrderStatusOutPut.paymentOrderNo = data.getPaymentOrderNo();
                    paymentOrderStatusOutPut.orderId = data.getOrderId();
                    paymentOrderStatusOutPut.status = data.getStatus();
                    paymentOrderStatusOutPut.amount = data.getAmount();

                    return paymentOrderStatusOutPut;
                })
                .toList();
    }
}
