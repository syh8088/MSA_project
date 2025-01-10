package kiwi.shop.wallet.application.port.out;

import kiwi.shop.wallet.domain.WalletOutPut;

import java.util.List;

public interface GetWalletPort {

    boolean isAlreadyProcessWallet(String orderId);

    List<WalletOutPut> selectWalletListBySellerNoList(List<Long> sellerNoList);

    List<WalletOutPut> selectWalletListWithWalletTransactionsByWalletNoList(List<Long> walletNoList);
}
