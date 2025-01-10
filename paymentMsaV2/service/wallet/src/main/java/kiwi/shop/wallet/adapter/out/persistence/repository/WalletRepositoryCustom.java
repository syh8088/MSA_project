package kiwi.shop.wallet.adapter.out.persistence.repository;

import kiwi.shop.wallet.domain.WalletOutPut;

import java.util.List;

public interface WalletRepositoryCustom {


    List<WalletOutPut> selectWalletListBySellerNoList(List<Long> sellerNoList);

    List<WalletOutPut> selectWalletListByWalletNoList(List<Long> walletNoList);
}
