package kiwi.shop.orderquery.domain;

import kiwi.shop.common.event.payload.PaymentConfirmEventPayload;
import kiwi.shop.common.event.payload.PaymentConfirmOrderPayload;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import kiwi.shop.common.event.domain.PaymentEventType;
import kiwi.shop.common.event.domain.PaymentEventMethod;

@Getter
@NoArgsConstructor
public class PaymentEventQueryModel {

    private long paymentEventNo;
    private long memberNo;
    private String orderId;
    private String paymentKey;
    private String orderName;
    private PaymentEventMethod method;
    private PaymentEventType type;
    private LocalDateTime approvedDateTime;
    private boolean isPaymentDone;

    private List<PaymentOrderQueryModel> paymentOrderList;

    @Builder
    private PaymentEventQueryModel(long paymentEventNo, long memberNo, String orderId, String paymentKey, String orderName, PaymentEventMethod method, PaymentEventType type, LocalDateTime approvedDateTime, boolean isPaymentDone, List<PaymentOrderQueryModel> paymentOrderList) {
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

    public static Optional<PaymentEventQueryModel> of(PaymentConfirmEventPayload payload) {

        if (Objects.isNull(payload)) return Optional.empty();

        List<PaymentConfirmOrderPayload> paymentOrderList = payload.getPaymentOrderList();

        return Optional.ofNullable(PaymentEventQueryModel.builder()
                .paymentEventNo(payload.getPaymentEventNo())
                .memberNo(payload.getMemberNo())
                .orderId(payload.getOrderId())
                .paymentKey(payload.getPaymentKey())
                .orderName(payload.getOrderName())
                .method(payload.getMethod())
                .type(payload.getType())
                .approvedDateTime(payload.getApprovedDateTime())
                .isPaymentDone(payload.isPaymentDone())
                .paymentOrderList(PaymentOrderQueryModel.ofByPayload(paymentOrderList))
                .build());
    }

    public static Optional<PaymentEventQueryModel> of(PaymentEventResponse paymentEventResponse) {

        if (Objects.isNull(paymentEventResponse)) return Optional.empty();

        List<PaymentOrderResponse> paymentOrderList = paymentEventResponse.getPaymentOrderList();

        return Optional.ofNullable(PaymentEventQueryModel.builder()
                .paymentEventNo(paymentEventResponse.getPaymentEventNo())
                .memberNo(paymentEventResponse.getMemberNo())
                .orderId(paymentEventResponse.getOrderId())
                .paymentKey(paymentEventResponse.getPaymentKey())
                .orderName(paymentEventResponse.getOrderName())
                .method(paymentEventResponse.getMethod())
                .type(paymentEventResponse.getType())
                .approvedDateTime(paymentEventResponse.getApprovedDateTime())
                .isPaymentDone(paymentEventResponse.isPaymentDone())
                .paymentOrderList(PaymentOrderQueryModel.of(paymentOrderList))
                .build());
    }
}
