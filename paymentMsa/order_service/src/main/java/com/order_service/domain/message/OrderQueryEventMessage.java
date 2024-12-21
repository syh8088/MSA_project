package com.order_service.domain.message;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class OrderQueryEventMessage {

    private OrderQueryEventMessageType type;
    private Map<String, Object> payload;
    private Map<String, Object> metadata;

    @Builder
    private OrderQueryEventMessage(OrderQueryEventMessageType type, Map<String, Object> payload, Map<String, Object> metadata) {
        this.type = type;
        this.payload = payload;
        this.metadata = metadata;
    }

    @Builder
    private OrderQueryEventMessage(OrderQueryEventMessageType orderQueryEventMessageType, Map<String, Object> payload) {
        this.type = orderQueryEventMessageType;
        this.payload = payload;
    }

    public static OrderQueryEventMessage of(OrderQueryEventMessageType type, Map<String, Object> payload) {
        return new OrderQueryEventMessage(type, payload);
    }

    public static OrderQueryEventMessage of(OrderQueryEventMessageType type, Map<String, Object> payload, Map<String, Object> metadata) {
        return new OrderQueryEventMessage(type, payload, metadata);
    }

    public String orderId() {
        return (String) payload.get("orderId");
    }
}