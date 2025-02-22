package kiwi.shop.order.domain;

import kiwi.shop.order.domain.toss.TossPaymentConfirmationResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import kiwi.shop.common.event.domain.PaymentEventType;
import kiwi.shop.common.event.domain.PaymentEventMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class PaymentExtraDetailsOutPut {

    private PaymentEventMethod method;
    private PaymentEventType type;
    private LocalDateTime approvedAt;
    private String orderName;
    private PSPConfirmationStatus pspConfirmationStatus;
    private BigDecimal totalAmount;

    @Builder
    private PaymentExtraDetailsOutPut(PaymentEventMethod method, PaymentEventType type, LocalDateTime approvedAt, String orderName, PSPConfirmationStatus pspConfirmationStatus, BigDecimal totalAmount) {
        this.method = method;
        this.type = type;
        this.approvedAt = approvedAt;
        this.orderName = orderName;
        this.pspConfirmationStatus = pspConfirmationStatus;
        this.totalAmount = totalAmount;
    }

    public static PaymentExtraDetailsOutPut of(TossPaymentConfirmationResponse tossPaymentConfirmationResponse) {

        if (Objects.isNull(tossPaymentConfirmationResponse)) {
            return null;
        }

        return PaymentExtraDetailsOutPut.builder()
                .method(PaymentEventMethod.getByPaymentEventMethod(tossPaymentConfirmationResponse.getMethod()))
                .type(PaymentEventType.getByPaymentEventType(tossPaymentConfirmationResponse.getType()))
                .approvedAt(TimeConverter.convertStringDateTimeToLocalDateTime(tossPaymentConfirmationResponse.getApprovedAt()))
                .orderName(tossPaymentConfirmationResponse.getOrderName())
                .pspConfirmationStatus(PSPConfirmationStatus.get(tossPaymentConfirmationResponse.getStatus()))
                .totalAmount(BigDecimal.valueOf(tossPaymentConfirmationResponse.getTotalAmount()))
                .build();
    }
}
