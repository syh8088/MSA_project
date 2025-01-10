package kiwi.shop.wallet.application.port.in;

public interface SettlementUseCase {

    void settlementProcess(String orderId);
}