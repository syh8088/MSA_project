package com.order_service.domain.message;

import lombok.Getter;

@Getter
public enum OrderQueryEventMessageType {
    SUCCESS("주문 합계 저장 성공");

    private final String description;

    OrderQueryEventMessageType(String description) {
        this.description = description;
    }

}