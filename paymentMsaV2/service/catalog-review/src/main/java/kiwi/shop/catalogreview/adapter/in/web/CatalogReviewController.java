package kiwi.shop.catalogreview.adapter.in.web;

import kiwi.shop.catalogreview.adapter.in.web.request.InsertCatalogReviewRequest;
import kiwi.shop.catalogreview.application.port.in.CatalogReviewUseCase;
import kiwi.shop.catalogreview.domain.InsertCatalogReviewCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/catalog-reviews")
public class CatalogReviewController {

    private final CatalogReviewUseCase catalogReviewUseCase;

    @PostMapping()
    public ResponseEntity<?> insertCatalogReview(@RequestBody InsertCatalogReviewRequest insertCatalogReviewRequest) {

        InsertCatalogReviewCommand insertCatalogReviewCommand
                = InsertCatalogReviewCommand.of(
                    insertCatalogReviewRequest.getProductNo(),
                    insertCatalogReviewRequest.getMemberNo(),
                    insertCatalogReviewRequest.getTitle(),
                    insertCatalogReviewRequest.getContent(),
                    insertCatalogReviewRequest.getStarRatingType()
                );

        catalogReviewUseCase.insertCatalogReview(insertCatalogReviewCommand);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{catalogReviewNo}")
    public ResponseEntity<?> deleteCatalogReview(@PathVariable("catalogReviewNo") long catalogReviewNo) {

        catalogReviewUseCase.deleteCatalogReview(catalogReviewNo);

        return ResponseEntity.noContent().build();
    }

}
