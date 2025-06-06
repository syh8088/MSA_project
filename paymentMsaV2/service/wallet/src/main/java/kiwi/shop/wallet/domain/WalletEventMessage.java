package kiwi.shop.wallet.domain;

import kiwi.shop.wallet.domain.enums.WalletEventMessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class WalletEventMessage {

    private WalletEventMessageType type;
    private Map<String, Object> payload;
    private Map<String, Object> metadata;

    @Builder
    private WalletEventMessage(WalletEventMessageType type, Map<String, Object> payload, Map<String, Object> metadata) {
        this.type = type;
        this.payload = payload;
        this.metadata = metadata;
    }

    @Builder
    private WalletEventMessage(WalletEventMessageType walletEventMessageType, Map<String, Object> payload) {
        this.type = walletEventMessageType;
        this.payload = payload;
    }

    public static WalletEventMessage of(WalletEventMessageType type, Map<String, Object> payload) {
        return new WalletEventMessage(type, payload);
    }

    public String orderId() {
        return (String) payload.get("orderId");
    }
}