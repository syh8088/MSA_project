package com.seller_service.adapter.axon.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class RequestInsertSellerCommand {

    @TargetAggregateIdentifier
    private String id;

    private String sellerId;

    @Builder
    private RequestInsertSellerCommand(String id, String sellerId) {
        this.id = id;
        this.sellerId = sellerId;
    }

    public static RequestInsertSellerCommand of(String sellerId) {

        String id = UUID.randomUUID().toString();

        return RequestInsertSellerCommand.builder()
                .id(id)
                .sellerId(sellerId)
                .build();
    }
}
