package com.wallet_service.application.port.in;

import com.wallet_service.domain.WalletEventMessage;

public interface SettlementUseCase {

    WalletEventMessage settlementProcess(String orderId);
}