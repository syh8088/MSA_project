package com.seller_service.application.port.in;

import com.seller_service.domain.RequestPersistenceInsertSellerCommand;

public interface InsertSellerUseCase {

    void insertSeller(RequestPersistenceInsertSellerCommand insertSellerCommand);
}
