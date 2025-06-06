package com.seller_service.adapter.axon.event;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestInsertSellerEvent {

    private String requestInsertSellerId;
    private String sellerId;


    public RequestInsertSellerEvent(String requestInsertSellerId, String sellerId) {
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
