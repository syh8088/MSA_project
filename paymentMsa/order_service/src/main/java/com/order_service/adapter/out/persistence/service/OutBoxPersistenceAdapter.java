package com.order_service.adapter.out.persistence.service;

import com.common.WebAdapter;
import com.order_service.adapter.out.persistence.repository.OutBoxRepository;
import com.order_service.application.port.out.OutBoxStatusUpdatePort;
import com.order_service.domain.OutBoxStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class OutBoxPersistenceAdapter implements OutBoxStatusUpdatePort {

    private final OutBoxRepository outBoxRepository;

    @Transactional
    @Override
    public void updateStatusByIdempotencyKey(String orderId, OutBoxStatus outBoxStatus) {
        outBoxRepository.updateStatusByIdempotencyKey(orderId, outBoxStatus);
    }
}
