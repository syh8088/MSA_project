package com.wallet_service.application.port.out;

import com.wallet_service.domain.WalletOutPut;

import java.util.List;

public interface InsertWalletTransactionsPort {

    void insertWalletTransactionList(List<WalletOutPut> updatedWallets);
}
