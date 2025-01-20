package kiwi.shop.orderquery.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class OptimizedCacheLockProvider {

    private final StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "optimized-cache-lock::";
    private static final Duration LOCK_TTL = Duration.ofSeconds(3);

    public boolean lock(String key) {
        /**
        * redis 에 key로 "" 라는 value 를 추가합니다.
        * 이 때 해당 key 에 존재하는 값이 있다면 false 를 반환합니다.
        * key 에 존재하는 값이 없어서 새로운 (key, value)를 추가한다면 lock 을 획득해서 true 를 반환합니다.
        *
        * @return lock 획득 여부
        */
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(
                generateLockKey(key),
                "",
                LOCK_TTL
        ));
    }

    public void unlock(String key) {
        redisTemplate.delete(generateLockKey(key));
    }

    private String generateLockKey(String key) {
        return KEY_PREFIX + key;
    }
}
