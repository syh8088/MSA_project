package kiwi.shop.hotcatalog.adapter.out.persistence.service;

import kiwi.shop.hotcatalog.adapter.out.persistence.repository.HotProductLikeCountRedisRepository;
import kiwi.shop.hotcatalog.adapter.out.persistence.repository.HotProductReviewCountRedisRepository;
import kiwi.shop.hotcatalog.adapter.out.persistence.repository.HotProductReviewStarRatingSumRedisRepository;
import kiwi.shop.hotcatalog.application.port.out.HotProductScoreCalculatorPort;
import kiwi.shop.hotcatalog.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class HotProductScoreCalculatorPersistenceAdapter implements HotProductScoreCalculatorPort {

    private final HotProductLikeCountRedisRepository hotProductLikeCountRedisRepository;
    private final HotProductReviewCountRedisRepository hotProductReviewCountRedisRepository;
    private final HotProductReviewStarRatingSumRedisRepository hotProductReviewStarRatingSumRedisRepository;

    private static final long PRODUCT_LIKE_COUNT_WEIGHT = 2;
    private static final long PRODUCT_REVIEW_COUNT_WEIGHT = 3;
    private static final long PRODUCT_REVIEW_STAR_RATING_WEIGHT = 5;

    @Override
    public long calculateHotProductScore(long productNo) {

        Long productLikeCount = hotProductLikeCountRedisRepository.read(productNo);
        Long productReviewCount = hotProductReviewCountRedisRepository.read(productNo);
        Long productReviewStarRatingSum = hotProductReviewStarRatingSumRedisRepository.read(productNo);

        return productLikeCount * PRODUCT_LIKE_COUNT_WEIGHT
                + productReviewCount * PRODUCT_REVIEW_COUNT_WEIGHT
                + productReviewStarRatingSum * PRODUCT_REVIEW_STAR_RATING_WEIGHT
;
    }
}
