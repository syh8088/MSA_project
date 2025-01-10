package kiwi.shop.seller.application.port.out;


import kiwi.shop.seller.domain.RequestPersistenceInsertSellerCommand;

public interface InsertSellerPort {

    long insertSeller(RequestPersistenceInsertSellerCommand insertSellerCommand);
}
