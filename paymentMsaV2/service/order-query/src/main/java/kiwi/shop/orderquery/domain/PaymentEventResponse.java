package kiwi.shop.orderquery.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class PaymentEventResponse {

    private long paymentEventNo;
    private long memberNo;
    private String orderId;
    private String paymentKey;
    private String orderName;
    private PaymentEventMethod method;
    private PaymentEventType type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime approvedDateTime;
    private boolean isPaymentDone;

    private List<PaymentOrderResponse> paymentOrderList;

    @Builder
    private PaymentEventResponse(long paymentEventNo, long memberNo, String orderId, String paymentKey, String orderName, PaymentEventMethod method, PaymentEventType type, LocalDateTime approvedDateTime, boolean isPaymentDone, List<PaymentOrderResponse> paymentOrderList) {
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

    public static PaymentEventResponse of() {
        return new PaymentEventResponse();
    }

    public static PaymentEventResponse of(PaymentEventQueryModel paymentEventQueryModel) {

        if (Objects.isNull(paymentEventQueryModel)) return of();

        List<PaymentOrderQueryModel> paymentOrderList = paymentEventQueryModel.getPaymentOrderList();
        List<PaymentOrderResponse> paymentOrderResponses = PaymentOrderResponse.ofList(paymentOrderList);

        return PaymentEventResponse.builder()
                .paymentEventNo(paymentEventQueryModel.getPaymentEventNo())
                .memberNo(paymentEventQueryModel.getMemberNo())
                .orderId(paymentEventQueryModel.getOrderId())
                .paymentKey(paymentEventQueryModel.getPaymentKey())
                .orderName(paymentEventQueryModel.getOrderName())
                .method(paymentEventQueryModel.getMethod())
                .type(paymentEventQueryModel.getType())
                .approvedDateTime(paymentEventQueryModel.getApprovedDateTime())
                .isPaymentDone(paymentEventQueryModel.isPaymentDone())
                .paymentOrderList(paymentOrderResponses)
                .build();
    }


}
