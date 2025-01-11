package kiwi.shop.hotcatalog.adapter.in.web;

import kiwi.shop.hotcatalog.application.port.in.HotCatalogUseCase;
import kiwi.shop.hotcatalog.domain.SelectProductResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hot-products")
public class HotProductController {

    private final HotCatalogUseCase hotCatalogUseCase;

    @GetMapping()
    public ResponseEntity<SelectProductResponses> selectHotProductList() {

        Optional<SelectProductResponses> optionalSelectProductResponses = hotCatalogUseCase.selectHotProductList();
        if (optionalSelectProductResponses.isPresent()) {
            return ResponseEntity.ok().body(optionalSelectProductResponses.get());
        }

        return ResponseEntity.ok().body(SelectProductResponses.of(List.of()));
    }
}
