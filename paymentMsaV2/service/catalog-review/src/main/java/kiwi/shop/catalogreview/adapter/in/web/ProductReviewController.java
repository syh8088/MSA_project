package kiwi.shop.catalogreview.adapter.in.web;

import kiwi.shop.catalogreview.adapter.in.web.request.InsertCatalogReviewRequest;
import kiwi.shop.catalogreview.application.port.in.ProductReviewUseCase;
import kiwi.shop.catalogreview.domain.InsertProductReviewCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product-reviews")
public class ProductReviewController {

    private final ProductReviewUseCase productReviewUseCase;

    @PostMapping()
    public ResponseEntity<?> insertCatalogReview(@RequestBody InsertCatalogReviewRequest insertCatalogReviewRequest) {

        InsertProductReviewCommand insertProductReviewCommand
                = InsertProductReviewCommand.of(
                    insertCatalogReviewRequest.getProductNo(),
                    insertCatalogReviewRequest.getMemberNo(),
                    insertCatalogReviewRequest.getTitle(),
                    insertCatalogReviewRequest.getContent(),
                    insertCatalogReviewRequest.getStarRatingType()
                );

        productReviewUseCase.insertCatalogReview(insertProductReviewCommand);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productReviewNo}")
    public ResponseEntity<?> deleteCatalogReview(@PathVariable("productReviewNo") long productReviewNo) {

        productReviewUseCase.deleteProductReview(productReviewNo);

        return ResponseEntity.noContent().build();
    }

}
