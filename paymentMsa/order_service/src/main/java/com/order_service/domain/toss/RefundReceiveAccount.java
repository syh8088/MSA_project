package com.order_service.domain.toss;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefundReceiveAccount {
    private String bankCode;
    private String accountNumber;
    private String holderName;
}