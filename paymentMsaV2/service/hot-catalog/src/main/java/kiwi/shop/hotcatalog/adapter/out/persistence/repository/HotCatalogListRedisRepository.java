package kiwi.shop.hotcatalog.adapter.out.persistence.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@RequiredArgsConstructor
public class HotCatalogListRedisRepository {

    private final StringRedisTemplate redisTemplate;

    private static final String KEY_TIME_FORMAT = "hot-catalogs::list::%s";
    private static final String KEY_FORMAT = "hot-catalogs::list";

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public void registerHotCatalog(long productNo, Long score, long hotProductLimitCount, Duration hotProductExpireTtl) {

        /**
         * executePipelined() -> 레디스 서버에 네트워크 한번만 연결하면서 여러번 연산을 수행 할 수 있다.
         */
        redisTemplate.executePipelined((RedisCallback<?>) action -> {

            StringRedisConnection conn = (StringRedisConnection) action;
            String key = generateKey();
            conn.zAdd(key, score, String.valueOf(productNo));

            // 상위 10개의 인기글만 만들어서 유지하기 위해 설명한 limit 값으로 sorted set 설정 할 수 있습니다.
            conn.zRemRange(key, 0, - hotProductLimitCount - 1);
            conn.expire(key, hotProductExpireTtl.toSeconds());

            return null;
        });
    }

    public void remove(Long articleId) {
        redisTemplate.opsForZSet().remove(generateKey(), String.valueOf(articleId));
    }

    private String generateKey(LocalDateTime time) {
        return generateKey(TIME_FORMATTER.format(time));
    }

    private String generateKey(String dateStr) {
        return KEY_TIME_FORMAT.formatted(dateStr);
    }

    private String generateKey() {
        return KEY_FORMAT;
    }

    public List<Long> readAll() {
        return Objects.requireNonNull(
                        redisTemplate.opsForZSet()
                                .reverseRangeWithScores(generateKey(), 0, -1)
                )
                .stream()
                .peek(tuple ->
                        log.info("[HotArticleListRedisRepository.readAll] articleId={}, score={}", tuple.getValue(), tuple.getScore()))
                .map(ZSetOperations.TypedTuple::getValue)
                .filter(Objects::nonNull)
                .map(Long::valueOf)
                .toList();
    }
}
