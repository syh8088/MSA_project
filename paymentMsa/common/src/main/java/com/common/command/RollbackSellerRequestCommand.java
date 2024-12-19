package com.common.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RollbackSellerRequestCommand {

    private String sagaRollbackSellerId;
    private long sellerNo;
}
