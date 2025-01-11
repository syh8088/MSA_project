package kiwi.shop.hotcatalog.adapter.out.persistence.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Slf4j
@Repository
@RequiredArgsConstructor
public class HotCatalogLikeCountRedisRepository {

    private final StringRedisTemplate redisTemplate;

    // hot-article::article::{articleId}::like-count
    private static final String KEY_FORMAT = "hot-catalog::product::%s::like-count";

    public void updateCatalogLikeCount(long productNo, long likeCount, Duration ttl) {
        redisTemplate.opsForValue().set(generateKey(productNo), String.valueOf(likeCount), ttl);
    }

    public Long read(long productNo) {
        String result = redisTemplate.opsForValue().get(generateKey(productNo));
        return result == null ? 0L : Long.parseLong(result);
    }

    private String generateKey(long productNo) {
        return KEY_FORMAT.formatted(productNo);
    }
}
