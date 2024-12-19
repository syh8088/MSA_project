package com.seller_service.adapter.axon.aggregate;

import com.common.command.RollbackSellerRequestCommand;
import com.common.event.RequestDeleteSellerEvent;
import com.seller_service.adapter.axon.command.RequestInsertSellerCommand;
import com.seller_service.adapter.axon.event.RequestInsertSellerEvent;
import com.seller_service.application.port.out.DeleteSellerPort;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Data
public class SellerAggregate {

    @AggregateIdentifier
    private String id;

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.ALWAYS)
    public void handler(RequestInsertSellerCommand command) {

        this.id = command.getId();
        String requestInsertSellerId = UUID.randomUUID().toString();

        // Saga Start
        apply(new RequestInsertSellerEvent(requestInsertSellerId, command.getSellerId()));
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.ALWAYS)
    public void requestInsertWalletAggregate(
            RollbackSellerRequestCommand command,
            DeleteSellerPort deleteSellerPort
    ) {

        id = command.getSagaRollbackSellerId();

        // seller insert
        deleteSellerPort.deleteSeller(command.getSellerNo());

        String requestDeleteSellerId = UUID.randomUUID().toString();

        // Saga Start
        apply(new RequestDeleteSellerEvent(requestDeleteSellerId, command.getSagaRollbackSellerId(), command.getSellerNo()));
    }

    public SellerAggregate() {
    }
}
