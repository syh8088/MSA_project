package com.seller_service.adapter.in.web.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InsertSellerRequest {

    private String sellerId;

    @Builder
    private InsertSellerRequest(String sellerId) {
        this.sellerId = sellerId;
    }

    public static InsertSellerRequest of(String sellerId) {
        return new InsertSellerRequest(sellerId);
    }
}
