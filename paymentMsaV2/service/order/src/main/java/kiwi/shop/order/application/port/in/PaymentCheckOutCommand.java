package kiwi.shop.order.application.port.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class PaymentCheckOutCommand {

    private List<Long> productNoList = Arrays.asList(1L, 2L, 3L);
    private long memberNo;

    @Builder
    private PaymentCheckOutCommand(List<Long> productNoList, long memberNo) {
        this.productNoList = productNoList;
        this.memberNo = memberNo;
    }

    public static PaymentCheckOutCommand of(List<Long> productNoList, long memberNo) {
        return PaymentCheckOutCommand.builder()
                .productNoList(productNoList)
                .memberNo(memberNo)
                .build();
    }
}
