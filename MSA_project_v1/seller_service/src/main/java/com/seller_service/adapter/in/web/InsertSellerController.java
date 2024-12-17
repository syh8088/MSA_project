package com.seller_service.adapter.in.web;

import com.seller_service.adapter.in.web.request.InsertSellerRequest;
import com.seller_service.application.port.in.InsertSellerUseCase;
import com.seller_service.domain.InsertSellerCommand;
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

    @PostMapping("confirm")
    public ResponseEntity<?> insertSeller(@RequestBody InsertSellerRequest request) {

        InsertSellerCommand insertSellerCommand
                = InsertSellerCommand.of(request.getSellerId());
        insertSellerUseCase.insertSeller(insertSellerCommand);

        return ResponseEntity.noContent().build();
    }
}
