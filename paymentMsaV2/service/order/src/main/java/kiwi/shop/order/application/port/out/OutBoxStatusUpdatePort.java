package kiwi.shop.order.application.port.out;

import kiwi.shop.order.domain.OutBoxStatus;

public interface OutBoxStatusUpdatePort {

    void updateStatusByIdempotencyKey(String orderId, OutBoxStatus outBoxStatus);
}
