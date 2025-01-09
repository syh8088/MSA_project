package kiwi.shop.order.domain.message;

import lombok.Getter;

@Getter
public enum WalletEventMessageType {
    SUCCESS("정산 성공");

    private final String description;

    WalletEventMessageType(String description) {
        this.description = description;
    }

}