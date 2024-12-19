package com.wallet_service.application.port.out;

import com.wallet_service.domain.InsertWalletCommand;

public interface InsertWalletPort {

    boolean insertWallet(InsertWalletCommand insertWalletCommand);
}
