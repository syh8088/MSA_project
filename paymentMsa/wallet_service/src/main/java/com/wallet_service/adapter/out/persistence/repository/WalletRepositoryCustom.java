package com.wallet_service.adapter.out.persistence.repository;

import com.wallet_service.domain.WalletOutPut;

import java.util.List;

public interface WalletRepositoryCustom {


    List<WalletOutPut> selectWalletListBySellerNoList(List<Long> sellerNoList);

    List<WalletOutPut> selectWalletListByWalletNoList(List<Long> walletNoList);
}
