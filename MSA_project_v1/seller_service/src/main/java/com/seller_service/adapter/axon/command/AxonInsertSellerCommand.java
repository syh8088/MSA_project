package com.seller_service.adapter.axon.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class AxonInsertSellerCommand {

    @TargetAggregateIdentifier
    private String id;

    private String sellerId;

    @Builder
    private AxonInsertSellerCommand(String id, String sellerId) {
        this.id = id;
        this.sellerId = sellerId;
    }

    public static AxonInsertSellerCommand of(String sellerId) {

        String id = UUID.randomUUID().toString();

        return AxonInsertSellerCommand.builder()
                .id(id)
                .sellerId(sellerId)
                .build();
    }
}
