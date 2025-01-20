package kiwi.shop.orderquery.cache;

import kiwi.shop.common.dataserializer.DataSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

@Slf4j
@Component
@RequiredArgsConstructor
public class OptimizedCacheManager {

    private final StringRedisTemplate redisTemplate;
    private final OptimizedCacheLockProvider optimizedCacheLockProvider;

    /**
     * cache 키로 파라미터가 여러 개 전달될 수 있을 텐데 그거에 대한 구분자로 이렇게 콜론 두 개 선언
     */
    private static final String DELIMITER = "::";

    /**
     * 캐시 또는 원본 데이터에 대해서 처리된 결과
     *
     * @param type: 캐시가 어떤 타입인지
     * @param ttlSeconds:
     * @param args: 캐시에 대하여 유니크 하게 구분하기 위한 타입별로 파라미터
     * @param returnType: 오브젝트에 대한 리턴 타입
     * @param originDataSupplier: 원본 데이터를 가져오기 위한 OptimizedCacheOriginDataSupplier
     *                          캐시가 만료되었을 때 원본 데이터에서 요청 할 수 있는 그런 메소드를 가지고 있겠죠
     * @return
     * @throws Throwable
     */
    public Object process(
            String type,
            long ttlSeconds,
            Object[] args,
            Class<?> returnType,
            OptimizedCacheOriginDataSupplier<?> originDataSupplier
    ) throws Throwable {

        String key = generateKey(type, args);

        String cachedData = redisTemplate.opsForValue().get(key);

        if (cachedData == null) {
            return this.refresh(originDataSupplier, key, ttlSeconds);
        }

        OptimizedCache optimizedCache = DataSerializer.deserialize(cachedData, OptimizedCache.class);
        if (optimizedCache == null) {
            return this.refresh(originDataSupplier, key, ttlSeconds);
        }

        /**
         * 캐시가 논리적으로 만료되지 않을 경우 그냥 데이터를 반환 한다.
         */
        if (!optimizedCache.isExpired()) {
            return optimizedCache.parseData(returnType);
        }

        /**
         * 캐시가 논리적으로 만료되었을 경우 갱신을 합니다.
         * 갱신을 위해서 한 건의 요청만 갈 수 있도록 락을 잡도록 합니다.
         */
        if (!optimizedCacheLockProvider.lock(key)) {

            /**
             * Lock 을 잡지 못 했으면 그냥 아무 데이터를 가지고 있으니깐 이거 그대로 반환 합니다.
             */
            return optimizedCache.parseData(returnType);
        }

        /**
         * --------- 여기서 부터는 ------------
         * 락을 획득한 한건의 요청만 여기로 올텐데 이건 이제 캐시를 refresh 합니다. (락을 획득 했으니깐)
         * 그리고 잘 처리 했으면 optimizedCacheLockProvider 에 unlock 을 합니다.
         */
        try {
            return refresh(originDataSupplier, key, ttlSeconds);
        }
        finally {
            optimizedCacheLockProvider.unlock(key);
        }
    }

    private Object refresh(OptimizedCacheOriginDataSupplier<?> originDataSupplier, String key, long ttlSeconds) throws Throwable {

        Object result = originDataSupplier.get();

        OptimizedCacheTTL optimizedCacheTTL = OptimizedCacheTTL.of(ttlSeconds);
        OptimizedCache optimizedCache = OptimizedCache.of(result, optimizedCacheTTL.getLogicalTTL());

        redisTemplate.opsForValue()
                .set(
                        key,
                        DataSerializer.serialize(optimizedCache),
                        optimizedCacheTTL.getPhysicalTTL()
                );

        return result;
    }

    /**
     * prefix 가 'a' 그리고 args = [1, 2] 로 들어오면
     * a::1::2 로 반환 한다.
     */
    private String generateKey(String prefix, Object[] args) {
        return prefix + DELIMITER +
                Arrays.stream(args)
                        .map(String::valueOf)
                        .collect(joining(DELIMITER));
    }

}
