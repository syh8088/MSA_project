package kiwi.shop.catalogreview.application.service;

import kiwi.shop.catalogreview.application.port.in.CatalogReviewUseCase;
import kiwi.shop.catalogreview.application.port.out.CatalogReviewPort;
import kiwi.shop.catalogreview.common.UseCase;
import kiwi.shop.catalogreview.domain.InsertCatalogReviewCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class CatalogReviewService implements CatalogReviewUseCase {

    private final CatalogReviewPort catalogReviewPort;

    @Transactional
    @Override
    public void insertCatalogReview(InsertCatalogReviewCommand insertCatalogReviewCommand) {

        catalogReviewPort.insertCatalogReview(insertCatalogReviewCommand);

    }
}
