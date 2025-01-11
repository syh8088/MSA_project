package kiwi.shop.cataloglike.adapter.out.persistence.service;

import kiwi.shop.cataloglike.adapter.out.persistence.entity.CatalogLike;
import kiwi.shop.cataloglike.adapter.out.persistence.repository.CatalogLikeMapper;
import kiwi.shop.cataloglike.adapter.out.persistence.repository.CatalogLikeRepository;
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
public class CatalogLikePersistenceAdapter implements CatalogLikePort {

    private final CatalogLikeRepository catalogLikeRepository;
    private final CatalogLikeMapper catalogLikeMapper;

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
}
