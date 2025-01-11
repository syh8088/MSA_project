package kiwi.shop.hotcatalog.adapter.out.persistence.service;

import kiwi.shop.hotcatalog.adapter.out.persistence.repository.HotCatalogLikeCountRedisRepository;
import kiwi.shop.hotcatalog.application.port.out.HotCatalogScoreCalculatorPort;
import kiwi.shop.hotcatalog.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class HotCatalogScoreCalculatorPersistenceAdapter implements HotCatalogScoreCalculatorPort {

    private final HotCatalogLikeCountRedisRepository hotCatalogLikeCountRedisRepository;

    private static final long CATALOG_LIKE_COUNT_WEIGHT = 2;
    private static final long CATALOG_REVIEW_COUNT_WEIGHT = 3;

    @Override
    public long calculateHotCatalogScore(long productNo) {

        Long productLikeCount = hotCatalogLikeCountRedisRepository.read(productNo);
//        Long productReviewCount = hotCatalogReviewCountRepository.read(productNo);

        return productLikeCount * CATALOG_LIKE_COUNT_WEIGHT
//                + productReviewCount * CATALOG_REVIEW_COUNT_WEIGHT
;
    }
}
