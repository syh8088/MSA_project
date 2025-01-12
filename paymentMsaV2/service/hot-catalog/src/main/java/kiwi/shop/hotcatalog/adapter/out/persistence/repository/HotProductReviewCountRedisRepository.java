package kiwi.shop.hotcatalog.adapter.out.persistence.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Objects;

@Slf4j
@Repository
@RequiredArgsConstructor
public class HotProductReviewCountRedisRepository {

    private final StringRedisTemplate redisTemplate;

    // hot-catalogs::catalogs::{productNo}::review-count
    private static final String KEY_FORMAT = "hot-catalogs::products::%s::review-counts";

    public void updateCatalogReviewCount(long productNo, long reviewCount, Duration ttl) {
        redisTemplate.opsForValue().set(generateKey(productNo), String.valueOf(reviewCount), ttl);
    }

    public Long read(long productNo) {
        String result = redisTemplate.opsForValue().get(generateKey(productNo));
        return (Objects.isNull(result)) ? 0L : Long.parseLong(result);
    }

    private String generateKey(long productNo) {
        return KEY_FORMAT.formatted(productNo);
    }
}
