package com.wallet_service.adapter.axon.aggregate;

//import com.common.InsertWalletCommandV2;
//import com.common.event.RequestInsertWalletFinishedEvent;
import com.wallet_service.adapter.axon.command.InsertWalletCommand;
import com.wallet_service.adapter.axon.event.RequestInsertWalletFinishedEvent;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

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
            InsertWalletCommand insertWalletCommand
    ) {
        System.out.println("FirmbankingRequestAggregate Handler");
        id = insertWalletCommand.getRequestInsertWalletId();

        // wallet insert

        // 0. 성공, 1. 실패
        apply(new RequestInsertWalletFinishedEvent(
                insertWalletCommand.getRequestInsertWalletId(),
                1,
                insertWalletCommand.getSellerNo(),
                insertWalletCommand.getSellerId()
        ));
    }


    public InsertSellerRequestInsertWalletAggregate() {
    }
}
