package kiwi.shop.orderquery.adapter.out.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.Limit;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class PaymentEventNoListRedisRepository {

    private final StringRedisTemplate redisTemplate;

    // article-read::members::{memberNo}::payment-event-ids
    private static final String KEY_FORMAT = "order-query::members::%s::payment-event-ids";

    public void add(long memberNo, long paymentEventNo, int limit) {

        /**
         * executePipelined() -> 레디스 서버에 네트워크 한번만 연결하면서 여러번 연산을 수행 할 수 있다.
         */
        redisTemplate.executePipelined((RedisCallback<?>) action -> {
            StringRedisConnection conn = (StringRedisConnection) action;
            String key = this.generateKey(memberNo);
            conn.zAdd(key, 0, this.toPaddedString(paymentEventNo));
            conn.zRemRange(key, 0, - limit - 1);
            return null;
        });
    }

    public void delete(long memberNo, long paymentEventNo) {
        redisTemplate.opsForZSet().remove(generateKey(memberNo), toPaddedString(paymentEventNo));
    }

    public List<Long> readAll(long memberNo, Long offset, int limit) {

        /**
         * Sorted Set => ZRANGE ${Key} 0 -1 REV
         */
        return Objects.requireNonNull(redisTemplate.opsForZSet()
                        .reverseRange(this.generateKey(memberNo), offset, offset + limit - 1))
                .stream().map(Long::valueOf).toList();
    }

    public List<Long> readAllInfiniteScroll(long memberNo, Long lastPaymentEventNo, int limit) {
        return Objects.requireNonNull(
            redisTemplate.opsForZSet().reverseRangeByLex(
                this.generateKey(memberNo),
                lastPaymentEventNo == null ?
                        Range.unbounded() :
                        Range.leftUnbounded(Range.Bound.exclusive(this.toPaddedString(lastPaymentEventNo))),
                Limit.limit().count(limit)
            )
        ).stream().map(Long::valueOf).toList();
    }

    private String toPaddedString(long paymentEventNo) {
        return "%019d".formatted(paymentEventNo);
        // 1234 -> 0000000000000001234
    }

    private String generateKey(long memberNo) {
        return KEY_FORMAT.formatted(memberNo);
    }
}
