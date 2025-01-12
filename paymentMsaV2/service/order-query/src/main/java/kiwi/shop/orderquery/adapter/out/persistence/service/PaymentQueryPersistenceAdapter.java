package kiwi.shop.orderquery.adapter.out.persistence.service;

import kiwi.shop.orderquery.adapter.out.persistence.repository.OrderQueryModelRedisRepository;
import kiwi.shop.orderquery.adapter.out.persistence.repository.PaymentEventNoListRedisRepository;
import kiwi.shop.orderquery.application.port.out.PostPaymentQueryPort;
import kiwi.shop.orderquery.common.WebAdapter;
import kiwi.shop.orderquery.domain.PaymentEventQueryModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class PaymentQueryPersistenceAdapter implements PostPaymentQueryPort {

    private final OrderQueryModelRedisRepository orderQueryModelRedisRepository;
    private final PaymentEventNoListRedisRepository paymentEventNoListRedisRepository;

    @Override
    public void insertOrderQuery(PaymentEventQueryModel paymentEventQueryModel) {
        orderQueryModelRedisRepository.create(paymentEventQueryModel, Duration.ofDays(1));
    }

    @Override
    public Optional<PaymentEventQueryModel> selectPaymentOrder(long memberNo, long paymentEventNo) {

        return orderQueryModelRedisRepository.read(memberNo, paymentEventNo);
    }

    @Override
    public Map<Long, PaymentEventQueryModel> selectPaymentEventWithOrderList(long memberNo, List<Long> paymentEventNoList) {

        return orderQueryModelRedisRepository.readAll(memberNo, paymentEventNoList);
    }

    @Override
    public void insertPaymentEventNoList(long memberNo, long paymentEventNo, int limit) {
        paymentEventNoListRedisRepository.add(memberNo, paymentEventNo, limit);
    }

    @Override
    public List<Long> readAllInfiniteScroll(long memberNo, Long lastPaymentEventNo, int limit) {
        return paymentEventNoListRedisRepository.readAllInfiniteScroll(memberNo, lastPaymentEventNo, limit);
    }
}
