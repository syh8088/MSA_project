package com.catalog_service.application.port.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class SelectProductListRequestCommand {

    private List<Long> productNoList;

    @Builder
    private SelectProductListRequestCommand(List<Long> productNoList) {
        this.productNoList = productNoList;
    }

    public static SelectProductListRequestCommand of(List<Long> productNoList) {
        return SelectProductListRequestCommand.builder()
                .productNoList(productNoList)
                .build();
    }

    public static SelectProductListRequestCommand of(long[] productNoList) {

        List<Long> collect = Arrays.stream(productNoList).boxed().collect(Collectors.toList());
        return SelectProductListRequestCommand.builder()
                .productNoList(collect)
                .build();
    }
}
