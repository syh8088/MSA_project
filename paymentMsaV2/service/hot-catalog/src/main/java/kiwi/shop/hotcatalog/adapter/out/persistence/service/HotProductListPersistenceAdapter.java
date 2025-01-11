package kiwi.shop.hotcatalog.adapter.out.persistence.service;

import kiwi.shop.hotcatalog.adapter.out.persistence.repository.HotProductListRedisRepository;
import kiwi.shop.hotcatalog.application.port.out.HotProductListPort;
import kiwi.shop.hotcatalog.client.CatalogClient;
import kiwi.shop.hotcatalog.common.WebAdapter;
import kiwi.shop.hotcatalog.domain.SelectProductResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class HotProductListPersistenceAdapter implements HotProductListPort {

    private final HotProductListRedisRepository hotProductListRedisRepository;
    private final CatalogClient catalogClient;

    @Override
    public void registerHotProduct(long productNo, long score, long hotProductLimitCount, Duration hotProductExpireTtl) {
        hotProductListRedisRepository.registerHotProduct(productNo, score, hotProductLimitCount, hotProductExpireTtl);
    }

    @Override
    public Optional<SelectProductResponses> selectHotProductList() {

        List<Long> productNoList = hotProductListRedisRepository.selectHotProductList();
        if (Objects.isNull(productNoList) || productNoList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(catalogClient.selectProducts(productNoList));
    }
}
