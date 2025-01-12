package kiwi.shop.order.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PaymentEventOutPut {

    private long paymentEventNo;
    private long memberNo;
    private String orderId;
    private String paymentKey;
    private String orderName;
    private PaymentEventMethod method;
    private PaymentEventType type;
    private LocalDateTime approvedDateTime;
    private boolean isPaymentDone;

    private List<PaymentOrderOutPut> paymentOrderList;

    @QueryProjection
    public PaymentEventOutPut(long paymentEventNo, long memberNo, String orderId, String paymentKey, String orderName, PaymentEventMethod method, PaymentEventType type, LocalDateTime approvedDateTime, boolean isPaymentDone) {
        this.paymentEventNo = paymentEventNo;
        this.memberNo = memberNo;
        this.orderId = orderId;
        this.paymentKey = paymentKey;
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
