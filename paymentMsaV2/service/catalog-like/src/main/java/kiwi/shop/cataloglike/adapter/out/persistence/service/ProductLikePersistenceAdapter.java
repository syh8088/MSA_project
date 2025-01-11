package kiwi.shop.cataloglike.adapter.out.persistence.service;

import kiwi.shop.cataloglike.adapter.out.persistence.entity.ProductLike;
import kiwi.shop.cataloglike.adapter.out.persistence.entity.ProductLikeCount;
import kiwi.shop.cataloglike.adapter.out.persistence.repository.ProductLikeCountRepository;
import kiwi.shop.cataloglike.adapter.out.persistence.repository.ProductLikeMapper;
import kiwi.shop.cataloglike.adapter.out.persistence.repository.ProductLikeRepository;
import kiwi.shop.cataloglike.application.port.out.ProductLikeCountPort;
import kiwi.shop.cataloglike.application.port.out.ProductLikePort;
import kiwi.shop.cataloglike.common.WebAdapter;
import kiwi.shop.cataloglike.domain.ProductLikeCommand;
import kiwi.shop.cataloglike.domain.ProductUnLikeCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class ProductLikePersistenceAdapter implements ProductLikePort, ProductLikeCountPort {

    private final ProductLikeRepository productLikeRepository;
    private final ProductLikeMapper productLikeMapper;
    private final ProductLikeCountRepository productLikeCountRepository;

    @Override
    public void like(ProductLikeCommand productLikeCommand) {

        productLikeMapper.insertProductLike(
                productLikeCommand.getProductLikeNo(),
                productLikeCommand.getProductNo(),
                productLikeCommand.getMemberNo(),
                productLikeCommand.getCreatedAt()
        );
    }

    @Override
    public void unlike(ProductUnLikeCommand productUnLikeCommand) {

        productLikeMapper.deleteProductLike(
                productUnLikeCommand.getProductNo(),
                productUnLikeCommand.getMemberNo()
        );
    }

    @Override
    public Optional<ProductLike> selectProductLikeByProductNoAndMemberNo(long productNo, long memberNo) {
        return productLikeRepository.selectProductLikeByProductNoAndMemberNo(productNo, memberNo);
    }

    @Override
    public void increase(long productNo) {

        ProductLikeCount productLikeCount = productLikeCountRepository.findById(productNo)
                .orElseGet(() -> ProductLikeCount.of(productNo, 0L));

        productLikeCount.increase();

        productLikeCountRepository.save(productLikeCount);
    }

    @Override
    public void decrease(long productNo) {

        ProductLikeCount productLikeCount = productLikeCountRepository.findById(productNo).orElseThrow();
        productLikeCount.decrease();
    }

    @Override
    public long selectProductLikeCountByProductNo(long productNo) {
        return productLikeCountRepository.findById(productNo)
                .map(ProductLikeCount::getLikeCount)
                .orElse(0L);
    }
}
