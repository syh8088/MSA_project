package kiwi.shop.wallet.application.port.out;

import kiwi.shop.wallet.domain.WalletOutPut;

import java.util.List;

public interface InsertWalletTransactionsPort {

    void insertWalletTransactionList(List<WalletOutPut> updatedWallets);
}
