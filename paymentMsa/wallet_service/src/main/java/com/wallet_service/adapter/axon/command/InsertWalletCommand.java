package com.wallet_service.adapter.axon.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertWalletCommand {

    @TargetAggregateIdentifier
    private String aggregateIdentifier;
    private String requestInsertWalletId;
    private long sellerNo;
    private String sellerId;

//    public InsertWalletCommand(String aggregateIdentifier, String requestInsertWalletId, long sellerNo, String sellerId) {
//        this.aggregateIdentifier = aggregateIdentifier;
//        this.requestInsertWalletId = requestInsertWalletId;
//        this.sellerNo = sellerNo;
//        this.sellerId = sellerId;
//    }
}
