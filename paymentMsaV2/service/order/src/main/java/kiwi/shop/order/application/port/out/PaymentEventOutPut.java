package kiwi.shop.order.application.port.out;

import com.querydsl.core.annotations.QueryProjection;
import kiwi.shop.order.domain.PaymentEventMethod;
import kiwi.shop.order.domain.PaymentEventType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PaymentEventOutPut {

    private long paymentEventId;
    private String paymentKey;
    private String orderId;
    private String orderName;
    private PaymentEventMethod method;
    private PaymentEventType type;
    private LocalDateTime approvedDateTime;
    private boolean isPaymentDone;

    private List<PaymentOrderOutPut> paymentOrderList;

    @QueryProjection
    public PaymentEventOutPut(long paymentEventId, String paymentKey, String orderId, String orderName, PaymentEventMethod method, PaymentEventType type, LocalDateTime approvedDateTime, boolean isPaymentDone) {
        this.paymentEventId = paymentEventId;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.orderName = orderName;
        this.method = method;
        this.type = type;
        this.approvedDateTime = approvedDateTime;
        this.isPaymentDone = isPaymentDone;
    }

    public static List<String> extractOrderIdList(List<PaymentEventOutPut> paymentEventList) {
        return paymentEventList.stream()
                .map(PaymentEventOutPut::getOrderId)
                .toList();
    }

    public void updatePaymentOrder(List<PaymentOrderOutPut> paymentOrderOutPuts) {
        this.paymentOrderList = paymentOrderOutPuts;
    }

    public BigDecimal getTotalAmount() {
        return this.paymentOrderList
                .stream()
                .map(PaymentOrderOutPut::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
