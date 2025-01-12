package kiwi.shop.orderquery.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PaymentEventWithOrderListCommand {

    private Long paymentEventNo;
    private LocalDateTime createdDateTime;
    private long memberNo;
    private int limit;

    @Builder
    private PaymentEventWithOrderListCommand(Long paymentEventNo, LocalDateTime createdDateTime, long memberNo, int limit) {
        this.paymentEventNo = paymentEventNo;
        this.createdDateTime = createdDateTime;
        this.memberNo = memberNo;
        this.limit = limit;
    }

    public static PaymentEventWithOrderListCommand of(Long paymentEventNo, LocalDateTime createdDateTime, long memberNo, int limit) {
        return PaymentEventWithOrderListCommand.builder()
                .paymentEventNo(paymentEventNo)
                .createdDateTime(createdDateTime)
                .memberNo(memberNo)
                .limit(limit)
                .build();
    }
}
