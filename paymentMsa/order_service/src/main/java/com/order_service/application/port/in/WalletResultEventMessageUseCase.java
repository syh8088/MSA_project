package com.order_service.application.port.in;

public interface WalletResultEventMessageUseCase {

    void execute(String orderId);
}
