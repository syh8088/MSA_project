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

    @Builder
    private PaymentCheckOutCommand(List<Long> productNoList) {
        this.productNoList = productNoList;
    }

    public static PaymentCheckOutCommand of(List<Long> productNoList) {
        return PaymentCheckOutCommand.builder()
                .productNoList(productNoList)
                .build();
    }
}
