package com.seller_service.adapter.axon.event;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SagaInsertWalletEvent {

    private String requestInsertWalletId;
    private String sagaInsertSellerId;
    private String requestInsertSellerId;
    private long sellerNo;
    private String sellerId;


    public SagaInsertWalletEvent(String requestInsertWalletId, String sagaInsertSellerId, String requestInsertSellerId, long sellerNo, String sellerId) {
        this.requestInsertWalletId = requestInsertWalletId;
        this.sagaInsertSellerId = sagaInsertSellerId;
        this.requestInsertSellerId = requestInsertSellerId;
        this.sellerNo = sellerNo;
        this.sellerId = sellerId;
    }

//    public static AxonInsertSellerEvent of(String id, String sellerId) {
//        return AxonInsertSellerEvent.builder()
//                .id(id)
//                .sellerId(sellerId)
//                .build();
//    }
}
