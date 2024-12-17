package com.wallet_service.application.port.out;

import com.wallet_service.domain.WalletOutPut;

import java.util.List;

public interface UpdateWalletPort {

    void updateWalletList(List<WalletOutPut> updatedWallets);
}
