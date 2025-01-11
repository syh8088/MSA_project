package kiwi.shop.cataloglike.adapter.out.persistence.service;

import kiwi.shop.cataloglike.adapter.out.persistence.entity.CatalogLikeCount;
import kiwi.shop.cataloglike.adapter.out.persistence.repository.CatalogLikeCountRepository;
import kiwi.shop.cataloglike.application.port.out.CatalogLikeCountPort;
import kiwi.shop.cataloglike.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class CatalogLikeCountPersistenceAdapter implements CatalogLikeCountPort {

    private final CatalogLikeCountRepository catalogLikeCountRepository;

    @Override
    public void increase(long productNo) {

        CatalogLikeCount catalogLikeCount = catalogLikeCountRepository.findById(productNo)
                .orElseGet(() -> CatalogLikeCount.of(productNo, 0L));

        catalogLikeCount.increase();

        catalogLikeCountRepository.save(catalogLikeCount);
    }

    @Override
    public void decrease(long productNo) {

        CatalogLikeCount catalogLikeCount = catalogLikeCountRepository.findById(productNo).orElseThrow();
        catalogLikeCount.decrease();
    }

    @Override
    public long selectProductLikeCountByProductNo(long productNo) {
        return catalogLikeCountRepository.findById(productNo)
                .map(CatalogLikeCount::getLikeCount)
                .orElse(0L);
    }

}
