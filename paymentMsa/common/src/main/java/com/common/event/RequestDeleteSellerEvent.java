package com.common.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class RequestDeleteSellerEvent {

    private String requestDeleteSellerId;
    private String sagaRollbackSellerId;
    private long sellerNo;

    public RequestDeleteSellerEvent(String requestDeleteSellerId, String sagaRollbackSellerId, long sellerNo) {
        this.requestDeleteSellerId = requestDeleteSellerId;
        this.sagaRollbackSellerId = sagaRollbackSellerId;
        this.sellerNo = sellerNo;
    }

    //    @Builder
//    private RequestDeleteSellerEvent(String requestDeleteSellerId, long sellerNo) {
//        this.requestDeleteSellerId = requestDeleteSellerId;
//        this.sellerNo = sellerNo;
//    }
//
//    public static RequestDeleteSellerEvent of(String requestDeleteSellerId, long sellerNo) {
//        return new RequestDeleteSellerEvent(requestDeleteSellerId, sellerNo);
//    }
}
