package com.seller_service.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestPersistenceInsertSellerCommand {

    private String sellerId;

    @Builder
    private RequestPersistenceInsertSellerCommand(String sellerId) {
        this.sellerId = sellerId;
    }

    public static RequestPersistenceInsertSellerCommand of(String sellerId) {
        return new RequestPersistenceInsertSellerCommand(sellerId);
    }
}
