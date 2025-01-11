package kiwi.shop.cataloglike.adapter.in.web;

import kiwi.shop.cataloglike.application.port.in.CatalogLikeUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/catalog-likes")
public class CatalogLikeController {

    private final CatalogLikeUseCase catalogLikeUseCase;

    @PostMapping("/products/{productNo}/members/{memberNo}")
    public ResponseEntity<?> likeCatalog(
            @PathVariable("productNo") long productNo,
            @PathVariable("memberNo") long memberNo
    ) {

        catalogLikeUseCase.like(productNo, memberNo);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/products/{productNo}/members/{memberNo}")
    public ResponseEntity<?> unlikeCatalog(
            @PathVariable("productNo") long productNo,
            @PathVariable("memberNo") long memberNo
    ) {
        catalogLikeUseCase.unlike(productNo, memberNo);

        return ResponseEntity.noContent().build();
    }

}
