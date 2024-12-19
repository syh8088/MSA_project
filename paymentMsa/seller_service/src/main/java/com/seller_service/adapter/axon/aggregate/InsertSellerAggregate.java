package com.seller_service.adapter.axon.aggregate;

import com.seller_service.adapter.axon.event.SagaInsertWalletEvent;
import com.seller_service.application.port.out.InsertSellerPort;
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
@Aggregate
@Data
public class InsertSellerAggregate {

    @AggregateIdentifier
    private String id;

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.ALWAYS)
    public void requestInsertWalletAggregate(
            com.common.command.InsertSellerCommand command,
            InsertSellerPort insertSellerPort
    ) {
        id = command.getId();

        // seller insert
//        long savedSellerNo = insertSellerPort.insertSeller(
//                InsertSellerCommand.of(command.getSellerId())
//        );
        long savedSellerNo = 9;
        String requestInsertWalletId = UUID.randomUUID().toString();

        // Saga Start
        apply(new SagaInsertWalletEvent(
                requestInsertWalletId,
                command.getSagaInsertSellerId(),
                command.getRequestInsertSellerId(),
                savedSellerNo,
                command.getSellerId()
        ));
    }

    public InsertSellerAggregate() {
    }
}
