package com.order_service.adapter.out.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class PaymentOrderRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String ORDERS_SELLERS_SUM_KEY = "orders:sellers:%s:sum";

    public void updatePaymentOrderAmountSumGropingSellerNo(long sellerNo, long productNo, double totalAmount) {
        this.zSetAdd(ORDERS_SELLERS_SUM_KEY, sellerNo, productNo, totalAmount);
    }

    public ZSetOperations<String, String> redisTemplateOpsForZset() {
        return redisTemplate.opsForZSet();
    }

    public Boolean zSetAdd(String key, long queue, long productNo, double totalAmount) {
        return this.redisTemplateOpsForZset().add(key.formatted(queue), String.valueOf(productNo), totalAmount);
    }

    public Set<ZSetOperations.TypedTuple<String>> selectOrderTotalAmountGroupingProductBySellerNo(long sellerNo) {
        String key = this.createRedisOrderKey(sellerNo);
        return redisTemplate.opsForZSet().rangeWithScores(key, 0, -1);
    }

    private String createRedisOrderKey(long sellerNo) {
        return String.format(ORDERS_SELLERS_SUM_KEY, sellerNo);
    }
}
