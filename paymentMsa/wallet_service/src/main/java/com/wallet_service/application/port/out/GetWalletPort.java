package com.wallet_service.application.port.out;

import com.wallet_service.domain.WalletOutPut;

import java.util.List;

public interface GetWalletPort {

    boolean isAlreadyProcessWallet(String orderId);

    List<WalletOutPut> selectWalletListBySellerNoList(List<Long> sellerNoList);

    List<WalletOutPut> selectWalletListWithWalletTransactionsByWalletNoList(List<Long> walletNoList);
}
