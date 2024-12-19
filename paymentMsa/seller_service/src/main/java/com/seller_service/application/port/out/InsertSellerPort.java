package com.seller_service.application.port.out;

import com.seller_service.domain.InsertSellerCommand;

public interface InsertSellerPort {

    long insertSeller(InsertSellerCommand insertSellerCommand);
}
