package com.common.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertSellerCommand {

    @TargetAggregateIdentifier
    private String id;
    private String requestInsertSellerId;
    private String sellerId;
    private String sagaInsertSellerId;
}
