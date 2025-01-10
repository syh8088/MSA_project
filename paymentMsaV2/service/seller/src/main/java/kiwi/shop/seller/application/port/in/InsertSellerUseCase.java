package kiwi.shop.seller.application.port.in;

import kiwi.shop.seller.domain.RequestPersistenceInsertSellerCommand;

public interface InsertSellerUseCase {

    void insertSeller(RequestPersistenceInsertSellerCommand insertSellerCommand);
}
