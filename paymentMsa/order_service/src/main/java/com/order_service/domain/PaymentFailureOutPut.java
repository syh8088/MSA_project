package com.order_service.domain;

import com.order_service.domain.toss.TossFailureResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class PaymentFailureOutPut {

    private String errorCode;
    private String errorMessage;

    @Builder
    private PaymentFailureOutPut(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static PaymentFailureOutPut of(TossFailureResponse tossFailureResponse) {

        if (Objects.isNull(tossFailureResponse)) {
            return null;
        }

        return PaymentFailureOutPut.builder()
                .errorCode(tossFailureResponse.getCode())
                .errorMessage(tossFailureResponse.getMessage())
                .build();
    }

}