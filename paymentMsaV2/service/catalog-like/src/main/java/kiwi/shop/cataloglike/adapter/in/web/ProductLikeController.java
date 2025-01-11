package kiwi.shop.cataloglike.adapter.in.web;

import kiwi.shop.cataloglike.application.port.in.ProductLikeUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/catalog-likes")
public class ProductLikeController {

    private final ProductLikeUseCase productLikeUseCase;

    @PostMapping("/products/{productNo}/members/{memberNo}")
    public ResponseEntity<?> likeCatalog(
            @PathVariable("productNo") long productNo,
            @PathVariable("memberNo") long memberNo
    ) {

        productLikeUseCase.like(productNo, memberNo);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/products/{productNo}/members/{memberNo}")
    public ResponseEntity<?> unlikeCatalog(
            @PathVariable("productNo") long productNo,
            @PathVariable("memberNo") long memberNo
    ) {
        productLikeUseCase.unlike(productNo, memberNo);

        return ResponseEntity.noContent().build();
    }

}
