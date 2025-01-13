package kiwi.shop.order.adapter.in.web.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class PaymentCheckOutRequest {

    private List<Long> productNoList = Arrays.asList(1L, 2L, 3L);
    private long memberNo = 1;

    @Builder
    private PaymentCheckOutRequest(List<Long> productNoList, long memberNo) {
        this.productNoList = productNoList;
        this.memberNo = memberNo;
    }

    public static PaymentCheckOutRequest of(List<Long> productNoList, long memberNo) {
        return PaymentCheckOutRequest.builder()
                .productNoList(productNoList)
                .memberNo(memberNo)
                .build();
    }
}