package kiwi.shop.orderquery.adapter.out.persistence.repository;

import kiwi.shop.common.dataserializer.DataSerializer;
import kiwi.shop.orderquery.domain.PaymentEventQueryModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderQueryModelRedisRepository {

    private final StringRedisTemplate redisTemplate;

    // order-read::orders::{orders}
    private static final String KEY_FORMAT = "order-query::members::%s::payment-events::%s";

    public void create(PaymentEventQueryModel paymentEventQueryModel, Duration ttl) {
        redisTemplate.opsForValue()
                .set(this.generateKey(paymentEventQueryModel), Objects.requireNonNull(DataSerializer.serialize(paymentEventQueryModel)), ttl);
    }

    public void update(PaymentEventQueryModel paymentEventQueryModel) {
        redisTemplate.opsForValue().setIfPresent(
                this.generateKey(paymentEventQueryModel),
                Objects.requireNonNull(DataSerializer.serialize(paymentEventQueryModel))
        );
    }

    public void delete(long memberNo, long paymentEventNo) {
        redisTemplate.delete(this.generateKey(memberNo, paymentEventNo));
    }

    public Optional<PaymentEventQueryModel> read(long memberNo, long paymentEventNo) {
        return Optional.ofNullable(
                redisTemplate.opsForValue().get(this.generateKey(memberNo, paymentEventNo))
        ).map(json -> DataSerializer.deserialize(json, PaymentEventQueryModel.class));
    }

    private String generateKey(PaymentEventQueryModel paymentEventQueryModel) {
        return generateKey(paymentEventQueryModel.getMemberNo(), paymentEventQueryModel.getPaymentEventNo());
    }

    private String generateKey(long memberNo, long paymentEventNo) {
        return KEY_FORMAT.formatted(memberNo, paymentEventNo);
    }

    public Map<Long, PaymentEventQueryModel> readAll(long memberNo, List<Long> paymentEventNoList) {

        List<String> keyList = paymentEventNoList.stream()
                .map(paymentEventNo -> this.generateKey(memberNo, paymentEventNo))
                .toList();

        /**
         * 레디스에 multiGet() 이용해서 호출시 여러개 데이터를 한번에 조회 가능 합니다.
         */
        return Objects.requireNonNull(redisTemplate.opsForValue().multiGet(keyList))
                .stream()
                .filter(Objects::nonNull)
                .map(json -> DataSerializer.deserialize(json, PaymentEventQueryModel.class))
                .collect(toMap(PaymentEventQueryModel::getPaymentEventNo, identity()));
    }
}
