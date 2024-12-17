package com.order_service.application.port.out;

import com.order_service.domain.OutBoxStatus;

public interface OutBoxStatusUpdatePort {

    void updateStatusByIdempotencyKey(String orderId, OutBoxStatus outBoxStatus);
}
