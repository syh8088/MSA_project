package kiwi.shop.order.adapter.in.web.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import kiwi.shop.common.event.domain.PaymentEventMethod;
import kiwi.shop.common.event.domain.PaymentEventType;
import kiwi.shop.common.event.domain.PaymentEventWithOrderOutPut;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    public static PaymentEventResponse of(List<PaymentEventWithOrderOutPut> paymentEventWithOrderList) {

        if (Objects.isNull(paymentEventWithOrderList) || paymentEventWithOrderList.isEmpty()) {
            return PaymentEventResponse.of();
        }

        Map<Long, PaymentEventWithOrderOutPut> paymentEventWithOrderMap = paymentEventWithOrderList.stream()
                .collect(Collectors.toMap(PaymentEventWithOrderOutPut::getPaymentOrderNo, paymentEventWithOrder -> paymentEventWithOrder));

        PaymentEventWithOrderOutPut paymentEven = paymentEventWithOrderList.get(0);

        List<PaymentOrderResponse> paymentOrderResponseList = new ArrayList<>();

        for (Long paymentOrderNo : paymentEventWithOrderMap.keySet()) {

            PaymentEventWithOrderOutPut paymentEventWithOrderOutPut = paymentEventWithOrderMap.get(paymentOrderNo);
            PaymentOrderResponse paymentOrderResponse
                    = PaymentOrderResponse.of(
                            paymentEventWithOrderOutPut.getPaymentOrderNo(),
                            paymentEventWithOrderOutPut.getProductNo(),
                            paymentEventWithOrderOutPut.getAmount(),
                            paymentEventWithOrderOutPut.getStatus(),
                            paymentEventWithOrderOutPut.getProductName()
            );

            paymentOrderResponseList.add(paymentOrderResponse);
        }

        return PaymentEventResponse.builder()
                .paymentEventNo(paymentEven.getPaymentEventNo())
                .memberNo(paymentEven.getMemberNo())
                .orderId(paymentEven.getOrderId())
                .paymentKey(paymentEven.getPaymentKey())
                .orderName(paymentEven.getOrderName())
                .method(paymentEven.getMethod())
                .type(paymentEven.getType())
                .approvedDateTime(paymentEven.getApprovedDateTime())
                .isPaymentDone(paymentEven.isPaymentDone())
                .paymentOrderList(paymentOrderResponseList)
                .build();
    }

    public static List<PaymentEventResponse> ofList(List<PaymentEventWithOrderOutPut> paymentEventWithOrderList) {

        if (Objects.isNull(paymentEventWithOrderList) || paymentEventWithOrderList.isEmpty()) {
            return List.of();
        }

        Map<Long, List<PaymentEventWithOrderOutPut>> paymentEventWithOrderMap = paymentEventWithOrderList.stream()
                .collect(Collectors.groupingBy(PaymentEventWithOrderOutPut::getPaymentEventNo));

        List<PaymentEventResponse> paymentEventResponseList = new ArrayList<>();

        for (Long paymentEventNo : paymentEventWithOrderMap.keySet()) {
            List<PaymentEventWithOrderOutPut> paymentEventWithOrderOutPuts = paymentEventWithOrderMap.get(paymentEventNo);
            PaymentEventResponse paymentEventResponse = of(paymentEventWithOrderOutPuts);
            paymentEventResponseList.add(paymentEventResponse);
        }

        return paymentEventResponseList;
    }
}
