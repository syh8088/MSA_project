package kiwi.shop.hotcatalog.adapter.out.persistence.service;

import kiwi.shop.hotcatalog.adapter.out.persistence.repository.HotCatalogLikeCountRedisRepository;
import kiwi.shop.hotcatalog.application.port.out.CatalogLikeCountPort;
import kiwi.shop.hotcatalog.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class HotCatalogLikeCountPersistenceAdapter implements CatalogLikeCountPort {

    private final HotCatalogLikeCountRedisRepository hotCatalogLikeCountRedisRepository;

    @Override
    public void updateCatalogLikeCount(long productNo, long productLikeCount, Duration duration) {
        hotCatalogLikeCountRedisRepository.updateCatalogLikeCount(productNo, productLikeCount, duration);
    }
}
