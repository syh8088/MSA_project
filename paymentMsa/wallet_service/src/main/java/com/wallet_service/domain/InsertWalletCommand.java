package com.wallet_service.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InsertWalletCommand {

    private long sellerNo;

    @Builder
    private InsertWalletCommand(long sellerNo) {
        this.sellerNo = sellerNo;
    }

    public static InsertWalletCommand of(long sellerNo) {
        return new InsertWalletCommand(sellerNo);
    }
}
