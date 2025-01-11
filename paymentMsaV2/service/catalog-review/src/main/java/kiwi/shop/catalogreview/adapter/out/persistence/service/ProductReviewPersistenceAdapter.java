package kiwi.shop.catalogreview.adapter.out.persistence.service;

import kiwi.shop.catalogreview.adapter.in.web.request.StarRatingType;
import kiwi.shop.catalogreview.adapter.out.persistence.entity.ProductReview;
import kiwi.shop.catalogreview.adapter.out.persistence.entity.ProductReviewCount;
import kiwi.shop.catalogreview.adapter.out.persistence.repository.ProductReviewCountRepository;
import kiwi.shop.catalogreview.adapter.out.persistence.repository.ProductReviewRepository;
import kiwi.shop.catalogreview.application.port.out.ProductReviewCountPort;
import kiwi.shop.catalogreview.application.port.out.ProductReviewPort;
import kiwi.shop.catalogreview.common.WebAdapter;
import kiwi.shop.catalogreview.domain.ProductReviewCountOutPut;
import kiwi.shop.catalogreview.domain.InsertProductReviewCommand;
import kiwi.shop.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class ProductReviewPersistenceAdapter implements ProductReviewPort, ProductReviewCountPort {

    private final ProductReviewRepository productReviewRepository;
    private final ProductReviewCountRepository productReviewCountRepository;

    private final Snowflake snowflake = new Snowflake();

    @Override
    public ProductReview selectCatalogReviewThenThrowExceptionByProductReviewNo(long productReviewNo) {
        return productReviewRepository.findById(productReviewNo).orElseThrow(() -> new RuntimeException("not found product review"));
    }

    @Override
    public ProductReview insertCatalogReview(InsertProductReviewCommand insertProductReviewCommand) {

        ProductReview productReview = ProductReview.of(
                snowflake.nextId(),
                insertProductReviewCommand.getProductNo(),
                insertProductReviewCommand.getMemberNo(),
                insertProductReviewCommand.getTitle(),
                insertProductReviewCommand.getContent(),
                insertProductReviewCommand.getStarRatingType()
        );

        return productReviewRepository.save(productReview);
    }

    @Override
    public void deleteProductReview(long productReviewNo) {

        productReviewRepository.deleteByProductReviewNo(true, productReviewNo);
    }

    @Override
    public ProductReviewCount increase(long productNo, StarRatingType starRatingType) {

        ProductReviewCount productReviewCount = productReviewCountRepository.findById(productNo)
                .orElseGet(() -> ProductReviewCount.of(productNo, 0L, 0L));

        productReviewCount.reviewCountIncrease();
        productReviewCount.reviewStarRatingIncrease(starRatingType.getStarRating());

        return productReviewCountRepository.save(productReviewCount);
    }

    @Override
    public ProductReviewCount decrease(long productNo, long starRating) {

        ProductReviewCount productReviewCount = productReviewCountRepository
                .findById(productNo)
                .orElseThrow(() -> new RuntimeException("not found product review count"));

        productReviewCount.reviewCountDecrease();
        productReviewCount.reviewStarRatingDecrease(starRating);

        return productReviewCountRepository.save(productReviewCount);
    }

    @Override
    public ProductReviewCountOutPut selectCatalogReviewCountByProductNo(long productNo) {
        return productReviewCountRepository.selectCatalogReviewCountByProductNo(productNo);
    }
}
