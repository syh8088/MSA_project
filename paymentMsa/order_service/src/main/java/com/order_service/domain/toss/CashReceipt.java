package com.order_service.domain.toss;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CashReceipt {
    private String type;
    private String receiptKey;
    private String issueNumber;
    private String receiptUrl;
    private int amount;
    private int taxFreeAmount;
}