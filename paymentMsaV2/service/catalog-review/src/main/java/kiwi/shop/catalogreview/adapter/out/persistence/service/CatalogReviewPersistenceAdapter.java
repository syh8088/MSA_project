package kiwi.shop.catalogreview.adapter.out.persistence.service;

import kiwi.shop.catalogreview.adapter.in.web.request.StarRatingType;
import kiwi.shop.catalogreview.adapter.out.persistence.entity.CatalogReview;
import kiwi.shop.catalogreview.adapter.out.persistence.entity.CatalogReviewCount;
import kiwi.shop.catalogreview.adapter.out.persistence.repository.CatalogReviewCountRepository;
import kiwi.shop.catalogreview.adapter.out.persistence.repository.CatalogReviewRepository;
import kiwi.shop.catalogreview.application.port.out.CatalogReviewCountPort;
import kiwi.shop.catalogreview.application.port.out.CatalogReviewPort;
import kiwi.shop.catalogreview.common.WebAdapter;
import kiwi.shop.catalogreview.domain.CatalogReviewCountOutPut;
import kiwi.shop.catalogreview.domain.InsertCatalogReviewCommand;
import kiwi.shop.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class CatalogReviewPersistenceAdapter implements CatalogReviewPort, CatalogReviewCountPort {

    private final CatalogReviewRepository catalogReviewRepository;
    private final CatalogReviewCountRepository catalogReviewCountRepository;

    private final Snowflake snowflake = new Snowflake();

    @Override
    public CatalogReview selectCatalogReviewThenThrowExceptionByCatalogReviewNo(long catalogReviewNo) {
        return catalogReviewRepository.findById(catalogReviewNo).orElseThrow(() -> new RuntimeException("not found catalog review"));
    }

    @Override
    public CatalogReview insertCatalogReview(InsertCatalogReviewCommand insertCatalogReviewCommand) {

        CatalogReview catalogReview = CatalogReview.of(
                snowflake.nextId(),
                insertCatalogReviewCommand.getProductNo(),
                insertCatalogReviewCommand.getMemberNo(),
                insertCatalogReviewCommand.getTitle(),
                insertCatalogReviewCommand.getContent(),
                insertCatalogReviewCommand.getStarRatingType()
        );

        return catalogReviewRepository.save(catalogReview);
    }

    @Override
    public void deleteCatalogReview(long catalogReviewNo) {

        catalogReviewRepository.deleteByCatalogReviewNo(catalogReviewNo);
    }

    @Override
    public CatalogReviewCount increase(long productNo, StarRatingType starRatingType) {

        CatalogReviewCount catalogReviewCount = catalogReviewCountRepository.findById(productNo)
                .orElseGet(() -> CatalogReviewCount.of(productNo, 0L, BigDecimal.ZERO));

        catalogReviewCount.reviewCountIncrease();
        catalogReviewCount.reviewStarRatingIncrease(starRatingType.getStarRating());

        return catalogReviewCountRepository.save(catalogReviewCount);
    }

    @Override
    public CatalogReviewCount decrease(long productNo, BigDecimal starRating) {

        CatalogReviewCount catalogReviewCount = catalogReviewCountRepository
                .findById(productNo)
                .orElseThrow(() -> new RuntimeException("not found catalog review count"));

        catalogReviewCount.reviewCountDecrease();
        catalogReviewCount.reviewStarRatingDecrease(starRating);

        return catalogReviewCountRepository.save(catalogReviewCount);
    }

    @Override
    public CatalogReviewCountOutPut selectCatalogReviewCountByProductNo(long productNo) {
        return catalogReviewCountRepository.selectCatalogReviewCountByProductNo(productNo);
    }
}
