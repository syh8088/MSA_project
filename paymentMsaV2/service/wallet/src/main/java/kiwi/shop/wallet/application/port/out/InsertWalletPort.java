package kiwi.shop.wallet.application.port.out;

import kiwi.shop.wallet.domain.InsertWalletCommand;

public interface InsertWalletPort {

    boolean insertWallet(InsertWalletCommand insertWalletCommand);
}
