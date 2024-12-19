package com.common.event;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestInsertWalletFinishedEvent {

    private String requestResultInsertWalletId;
    private String sagaInsertWalletId;
    private String sagaInsertSellerId;
    private boolean isSuccess;
    private long sellerNo;
    private String sellerId;

    public RequestInsertWalletFinishedEvent(String requestResultInsertWalletId, String sagaInsertWalletId, String sagaInsertSellerId, boolean isSuccess, long sellerNo, String sellerId) {
        this.requestResultInsertWalletId = requestResultInsertWalletId;
        this.sagaInsertWalletId = sagaInsertWalletId;
        this.sagaInsertSellerId = sagaInsertSellerId;
        this.isSuccess = isSuccess;
        this.sellerNo = sellerNo;
        this.sellerId = sellerId;
    }
}
