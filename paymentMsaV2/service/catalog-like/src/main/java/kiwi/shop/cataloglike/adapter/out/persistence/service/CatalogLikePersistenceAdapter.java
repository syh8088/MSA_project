package kiwi.shop.cataloglike.adapter.out.persistence.service;

import kiwi.shop.cataloglike.adapter.out.persistence.entity.CatalogLike;
import kiwi.shop.cataloglike.adapter.out.persistence.entity.CatalogLikeCount;
import kiwi.shop.cataloglike.adapter.out.persistence.repository.CatalogLikeCountRepository;
import kiwi.shop.cataloglike.adapter.out.persistence.repository.CatalogLikeMapper;
import kiwi.shop.cataloglike.adapter.out.persistence.repository.CatalogLikeRepository;
import kiwi.shop.cataloglike.application.port.out.CatalogLikeCountPort;
import kiwi.shop.cataloglike.application.port.out.CatalogLikePort;
import kiwi.shop.cataloglike.common.WebAdapter;
import kiwi.shop.cataloglike.domain.CatalogLikeCommand;
import kiwi.shop.cataloglike.domain.CatalogUnLikeCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class CatalogLikePersistenceAdapter implements CatalogLikePort, CatalogLikeCountPort {

    private final CatalogLikeRepository catalogLikeRepository;
    private final CatalogLikeMapper catalogLikeMapper;
    private final CatalogLikeCountRepository catalogLikeCountRepository;

    @Override
    public void like(CatalogLikeCommand catalogLikeCommand) {

        catalogLikeMapper.insertCatalogLike(
                catalogLikeCommand.getCatalogLikeNo(),
                catalogLikeCommand.getProductNo(),
                catalogLikeCommand.getMemberNo(),
                catalogLikeCommand.getCreatedAt()
        );
    }

    @Override
    public void unlike(CatalogUnLikeCommand catalogUnLikeCommand) {

        catalogLikeMapper.deleteCatalogLike(
                catalogUnLikeCommand.getProductNo(),
                catalogUnLikeCommand.getMemberNo()
        );
    }

    @Override
    public Optional<CatalogLike> selectCatalogLikeByProductNoAndMemberNo(long productNo, long memberNo) {
        return catalogLikeRepository.selectCatalogLikeByProductNoAndMemberNo(productNo, memberNo);
    }

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
