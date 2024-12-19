package com.common.event;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestIRollbackInsertWalletFinishedEvent {

    private String requestInsertWalletId;
    private long walletNo;
    private long sellerNo;
    private String sellerId;

    public RequestIRollbackInsertWalletFinishedEvent(String requestInsertWalletId, long walletNo, long sellerNo, String sellerId) {
        this.requestInsertWalletId = requestInsertWalletId;
        this.walletNo = walletNo;
        this.sellerNo = sellerNo;
        this.sellerId = sellerId;
    }
}
