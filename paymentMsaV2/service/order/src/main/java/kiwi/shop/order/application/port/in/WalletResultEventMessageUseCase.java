package kiwi.shop.order.application.port.in;

public interface WalletResultEventMessageUseCase {

    void execute(String orderId);
}
