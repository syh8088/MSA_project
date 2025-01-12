package kiwi.shop.hotcatalog.adapter.out.persistence.service;

import kiwi.shop.hotcatalog.adapter.out.persistence.repository.HotProductReviewCountRedisRepository;
import kiwi.shop.hotcatalog.adapter.out.persistence.repository.HotProductReviewStarRatingSumRedisRepository;
import kiwi.shop.hotcatalog.application.port.out.CatalogReviewCountPort;
import kiwi.shop.hotcatalog.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class HotCatalogReviewCountPersistenceAdapter implements CatalogReviewCountPort {

    private final HotProductReviewCountRedisRepository hotProductReviewCountRedisRepository;
    private final HotProductReviewStarRatingSumRedisRepository hotProductReviewStarRatingSumRedisRepository;

    @Override
    public void updateCatalogReviewCount(
            long productReviewNo,
            long productNo,
            long memberNo,
            String title,
            String content,
            long starRating,
            Boolean isDeleted,
            long reviewCount,
            long reviewStarRatingSum,
            Duration duration
    ) {

        hotProductReviewCountRedisRepository.updateCatalogReviewCount(productNo, reviewCount, duration);
        hotProductReviewStarRatingSumRedisRepository.updateCatalogReviewStarRatingSum(productNo, reviewStarRatingSum, duration);
    }

}
