package kiwi.shop.catalogreview.adapter.out.persistence.service;

import kiwi.shop.catalogreview.adapter.out.persistence.entity.CatalogReview;
import kiwi.shop.catalogreview.adapter.out.persistence.repository.CatalogReviewRepository;
import kiwi.shop.catalogreview.application.port.out.CatalogReviewPort;
import kiwi.shop.catalogreview.common.WebAdapter;
import kiwi.shop.catalogreview.domain.InsertCatalogReviewCommand;
import kiwi.shop.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class CatalogReviewPersistenceAdapter implements CatalogReviewPort {

    private final CatalogReviewRepository catalogReviewRepository;

    private final Snowflake snowflake = new Snowflake();

    @Override
    public void insertCatalogReview(InsertCatalogReviewCommand insertCatalogReviewCommand) {

        CatalogReview catalogReview = CatalogReview.of(
                snowflake.nextId(),
                insertCatalogReviewCommand.getProductNo(),
                insertCatalogReviewCommand.getMemberNo(),
                insertCatalogReviewCommand.getTitle(),
                insertCatalogReviewCommand.getContent(),
                insertCatalogReviewCommand.getStarRatingType()
        );

        catalogReviewRepository.save(catalogReview);
    }
}
