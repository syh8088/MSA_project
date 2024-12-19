package com.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
public class InsertWalletCommand {

    @TargetAggregateIdentifier
    private String aggregateIdentifier;
    private String sagaInsertWalletId;
    private String sagaInsertSellerId;
    private long sellerNo;
    private String sellerId;

    public InsertWalletCommand(String aggregateIdentifier, String sagaInsertWalletId, String sagaInsertSellerId, long sellerNo, String sellerId) {
        this.aggregateIdentifier = aggregateIdentifier;
        this.sagaInsertWalletId = sagaInsertWalletId;
        this.sagaInsertSellerId = sagaInsertSellerId;
        this.sellerNo = sellerNo;
        this.sellerId = sellerId;
    }
}
