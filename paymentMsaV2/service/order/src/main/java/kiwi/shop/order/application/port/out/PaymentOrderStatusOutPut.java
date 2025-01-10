package kiwi.shop.order.application.port.out;

import com.querydsl.core.annotations.QueryProjection;
import kiwi.shop.order.domain.PaymentOrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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


}
