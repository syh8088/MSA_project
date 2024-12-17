package com.seller_service.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InsertSellerCommand {

    private String sellerId;

    @Builder
    private InsertSellerCommand(String sellerId) {
        this.sellerId = sellerId;
    }

    public static InsertSellerCommand of(String sellerId) {
        return new InsertSellerCommand(sellerId);
    }
}
