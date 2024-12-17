package com.seller_service.application.service;

import com.common.UseCase;
import com.seller_service.application.port.in.InsertSellerUseCase;
import com.seller_service.application.port.out.InsertSellerPort;
import com.seller_service.domain.InsertSellerCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class SellerService implements InsertSellerUseCase {

    private final InsertSellerPort insertSellerPort;

    @Override
    public void insertSeller(InsertSellerCommand insertSellerCommand) {
        insertSellerPort.insertSeller(insertSellerCommand);
    }
}
