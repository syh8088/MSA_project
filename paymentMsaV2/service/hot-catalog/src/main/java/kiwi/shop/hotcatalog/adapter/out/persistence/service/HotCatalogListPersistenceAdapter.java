package kiwi.shop.hotcatalog.adapter.out.persistence.service;

import kiwi.shop.hotcatalog.adapter.out.persistence.repository.HotCatalogListRedisRepository;
import kiwi.shop.hotcatalog.application.port.out.HotCatalogListPort;
import kiwi.shop.hotcatalog.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class HotCatalogListPersistenceAdapter implements HotCatalogListPort {

    private final HotCatalogListRedisRepository hotCatalogListRedisRepository;

    @Override
    public void registerHotCatalog(long productNo, long score, long hotProductLimitCount, Duration hotProductExpireTtl) {
        hotCatalogListRedisRepository.registerHotCatalog(productNo, score, hotProductLimitCount, hotProductExpireTtl);
    }
}
