package kiwi.shop.common.event.payload;

import kiwi.shop.common.event.EventPayload;
import kiwi.shop.common.event.domain.PaymentEventMethod;
import kiwi.shop.common.event.domain.PaymentEventType;
import kiwi.shop.common.event.domain.PaymentEventWithOrderOutPut;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PaymentConfirmEventPayload implements EventPayload {

    private long paymentEventNo;
    private long memberNo;
    private String orderId;
    private String paymentKey;
    private String orderName;
    private PaymentEventMethod method;
    private PaymentEventType type;
    private LocalDateTime approvedDateTime;
    private boolean isPaymentDone;

    private List<PaymentConfirmOrderPayload> paymentOrderList;

    @Builder
    private PaymentConfirmEventPayload(long paymentEventNo, long memberNo, String orderId, String paymentKey, String orderName, PaymentEventMethod method, PaymentEventType type, LocalDateTime approvedDateTime, boolean isPaymentDone, List<PaymentConfirmOrderPayload> paymentOrderList) {
        this.paymentEventNo = paymentEventNo;
        this.memberNo = memberNo;
        this.orderId = orderId;
        this.paymentKey = paymentKey;
        this.orderName = orderName;
        this.method = method;
        this.type = type;
        this.approvedDateTime = approvedDateTime;
        this.isPaymentDone = isPaymentDone;
        this.paymentOrderList = paymentOrderList;
    }

    public static PaymentConfirmEventPayload of(List<PaymentEventWithOrderOutPut> paymentEventList) {

        PaymentEventWithOrderOutPut paymentEventWithOrder = paymentEventList.get(0);
        List<PaymentConfirmOrderPayload> paymentOrderList = PaymentConfirmOrderPayload.of(paymentEventList);

        return PaymentConfirmEventPayload.builder()
                .paymentEventNo(paymentEventWithOrder.getPaymentEventNo())
                .memberNo(paymentEventWithOrder.getMemberNo())
                .orderId(paymentEventWithOrder.getOrderId())
                .paymentKey(paymentEventWithOrder.getPaymentKey())
                .orderName(paymentEventWithOrder.getOrderName())
                .method(paymentEventWithOrder.getMethod())
                .type(paymentEventWithOrder.getType())
                .approvedDateTime(paymentEventWithOrder.getApprovedDateTime())
                .isPaymentDone(paymentEventWithOrder.isPaymentDone())
                .paymentOrderList(paymentOrderList)
                .build();
    }
}
