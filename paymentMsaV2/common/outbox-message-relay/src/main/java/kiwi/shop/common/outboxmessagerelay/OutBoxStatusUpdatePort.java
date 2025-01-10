package kiwi.shop.common.outboxmessagerelay;

public interface OutBoxStatusUpdatePort {

    void updateStatusByIdempotencyKey(String orderId, OutBoxStatus outBoxStatus);
}
