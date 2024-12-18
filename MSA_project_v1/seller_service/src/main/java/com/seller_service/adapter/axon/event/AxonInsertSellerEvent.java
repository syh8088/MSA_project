package com.seller_service.adapter.axon.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AxonInsertSellerEvent {

    private String requestInsertSellerId;
    private String sellerId;


    public AxonInsertSellerEvent(String requestInsertSellerId, String sellerId) {
        this.requestInsertSellerId = requestInsertSellerId;
        this.sellerId = sellerId;
    }

//    public static AxonInsertSellerEvent of(String id, String sellerId) {
//        return AxonInsertSellerEvent.builder()
//                .id(id)
//                .sellerId(sellerId)
//                .build();
//    }
}
