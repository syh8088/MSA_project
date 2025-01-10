package kiwi.shop.seller.application.service;

import kiwi.shop.seller.application.port.in.InsertSellerUseCase;
import kiwi.shop.seller.common.UseCase;
import kiwi.shop.seller.domain.RequestPersistenceInsertSellerCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class SellerService implements InsertSellerUseCase {

    @Override
    public void insertSeller(RequestPersistenceInsertSellerCommand insertSellerCommand) {

    }
}
