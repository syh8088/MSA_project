package com.seller_service.adapter.axon.aggregate;

import com.seller_service.adapter.axon.command.AxonInsertSellerCommand;
import com.seller_service.adapter.axon.event.AxonInsertSellerEvent;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
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
    public void handler(AxonInsertSellerCommand command) {

        // 이미 존재하는 SellerId 인지 체크 해야 합니다.
        this.id = command.getId();
        String requestInsertSellerId = UUID.randomUUID().toString();

        // Saga Start
        apply(new AxonInsertSellerEvent(requestInsertSellerId, command.getSellerId()));
    }

//    @EventSourcingHandler
//    public void on(AxonInsertSellerEvent event) {
//        System.out.println("MemberMoneyCreatedEvent Sourcing Handler");
//        this.id = event.getId();
//    }
//
//    @ExceptionHandler(resultType = IllegalStateException.class)
//    public void error(IllegalStateException exp) {
//        System.out.println(exp.getMessage());
//    }

    public SellerAggregate() {
    }
}
