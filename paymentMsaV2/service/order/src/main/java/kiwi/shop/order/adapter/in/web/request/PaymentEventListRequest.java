package kiwi.shop.order.adapter.in.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PaymentEventListRequest {

    private Long paymentEventNo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDateTime;

    private int limit;

    @Builder
    private PaymentEventListRequest(Long paymentEventNo, LocalDateTime createdDateTime, int limit) {
        this.paymentEventNo = paymentEventNo;
        this.createdDateTime = createdDateTime;
        this.limit = limit;
    }

    public static PaymentEventListRequest of(Long paymentEventNo, LocalDateTime createdDateTime, int limit) {
        return PaymentEventListRequest.builder()
                .paymentEventNo(paymentEventNo)
                .createdDateTime(createdDateTime)
                .limit(limit)
                .build();
    }
}
