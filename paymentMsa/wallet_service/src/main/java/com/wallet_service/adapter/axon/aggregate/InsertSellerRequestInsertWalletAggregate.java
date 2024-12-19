package com.wallet_service.adapter.axon.aggregate;

import com.common.InsertWalletCommand;
import com.common.event.RequestInsertWalletFinishedEvent;
import com.wallet_service.application.port.out.InsertWalletPort;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Aggregate()
@Data
public class InsertSellerRequestInsertWalletAggregate {

    @AggregateIdentifier
    private String id;

    private long sellerNo;
    private String sellerId;

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.ALWAYS)
    public void requestInsertWalletAggregate(
            InsertWalletCommand insertWalletCommand,
            InsertWalletPort insertWalletPort
    ) {

        id = insertWalletCommand.getAggregateIdentifier();

        // wallet insert
        boolean isSuccess = insertWalletPort.insertWallet(com.wallet_service.domain.InsertWalletCommand.of(insertWalletCommand.getSellerNo()));
//        boolean isSuccess = false;

        String requestResultInsertWalletId = UUID.randomUUID().toString();

        apply(new RequestInsertWalletFinishedEvent(
                requestResultInsertWalletId,
                insertWalletCommand.getSagaInsertWalletId(),
                insertWalletCommand.getSagaInsertSellerId(),
                isSuccess,
                insertWalletCommand.getSellerNo(),
                insertWalletCommand.getSellerId()
        ));
    }

    public InsertSellerRequestInsertWalletAggregate() {
    }
}
