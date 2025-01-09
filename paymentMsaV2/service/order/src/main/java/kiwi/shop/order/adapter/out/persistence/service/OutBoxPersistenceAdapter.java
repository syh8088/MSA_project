package kiwi.shop.order.adapter.out.persistence.service;

import kiwi.shop.order.adapter.out.persistence.repository.OutBoxRepository;
import kiwi.shop.order.application.port.out.OutBoxStatusUpdatePort;
import kiwi.shop.order.common.WebAdapter;
import kiwi.shop.order.domain.OutBoxStatus;
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
