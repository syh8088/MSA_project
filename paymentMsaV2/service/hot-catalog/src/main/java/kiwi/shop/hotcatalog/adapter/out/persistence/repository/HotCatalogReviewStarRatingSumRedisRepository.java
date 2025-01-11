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
public class HotCatalogReviewStarRatingSumRedisRepository {

    private final StringRedisTemplate redisTemplate;

    // hot-catalogs::catalogs::{productNo}::review-star-rating-sums
    private static final String KEY_FORMAT = "hot-catalogs::products::%s::review-star-rating-sums";

    public void updateCatalogReviewStarRatingSum(long productNo, long reviewStarRatingSum, Duration ttl) {
        redisTemplate.opsForValue().set(generateKey(productNo), String.valueOf(reviewStarRatingSum), ttl);
    }

    public Long read(long productNo) {
        String result = redisTemplate.opsForValue().get(generateKey(productNo));
        return (Objects.isNull(result)) ? 0L : Long.parseLong(result);
    }

    private String generateKey(long productNo) {
        return KEY_FORMAT.formatted(productNo);
    }

}
