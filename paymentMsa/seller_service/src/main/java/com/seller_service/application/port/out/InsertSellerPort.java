package com.seller_service.application.port.out;

import com.seller_service.domain.RequestPersistenceInsertSellerCommand;

public interface InsertSellerPort {

    long insertSeller(RequestPersistenceInsertSellerCommand insertSellerCommand);
}
