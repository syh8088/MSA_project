package kiwi.shop.seller.adapter.in.web;

import kiwi.shop.seller.adapter.in.web.request.InsertSellerRequest;
import kiwi.shop.seller.application.port.in.InsertSellerUseCase;
import kiwi.shop.seller.domain.RequestPersistenceInsertSellerCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sellers")
public class InsertSellerController {

    private final InsertSellerUseCase insertSellerUseCase;

    @PostMapping
    public ResponseEntity<?> insertSeller(@RequestBody InsertSellerRequest request) {

        RequestPersistenceInsertSellerCommand insertSellerCommand
                = RequestPersistenceInsertSellerCommand.of(request.getSellerId());
        insertSellerUseCase.insertSeller(insertSellerCommand);

        return ResponseEntity.noContent().build();
    }
}
