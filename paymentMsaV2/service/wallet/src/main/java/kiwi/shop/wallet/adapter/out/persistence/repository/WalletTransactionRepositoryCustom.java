package kiwi.shop.wallet.adapter.out.persistence.repository;


import kiwi.shop.wallet.domain.WalletTransactionOutPut;

import java.util.List;

public interface WalletTransactionRepositoryCustom {

    List<WalletTransactionOutPut> selectWalletTransactionListByWalletNo(long walletNo);
}
