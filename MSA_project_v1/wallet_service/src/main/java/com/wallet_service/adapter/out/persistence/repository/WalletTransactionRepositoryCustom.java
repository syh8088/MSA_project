package com.wallet_service.adapter.out.persistence.repository;


import com.wallet_service.domain.WalletTransactionOutPut;

import java.util.List;

public interface WalletTransactionRepositoryCustom {

    List<WalletTransactionOutPut> selectWalletTransactionListByWalletNo(long walletNo);
}
