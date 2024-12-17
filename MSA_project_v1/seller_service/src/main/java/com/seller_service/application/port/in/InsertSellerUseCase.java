package com.seller_service.application.port.in;

import com.seller_service.domain.InsertSellerCommand;

public interface InsertSellerUseCase {

    void insertSeller(InsertSellerCommand insertSellerCommand);
}
