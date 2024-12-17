package com.seller_service.adapter.out.persistence.service;

import com.common.WebAdapter;
import com.seller_service.adapter.out.persistence.entity.Seller;
import com.seller_service.adapter.out.persistence.repository.SellerRepository;
import com.seller_service.application.port.out.InsertSellerPort;
import com.seller_service.domain.InsertSellerCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class SellerPersistenceAdapter implements InsertSellerPort {

    private final SellerRepository sellerRepository;

    @Override
    public void insertSeller(InsertSellerCommand insertSellerCommand) {

        Seller seller = Seller.of(insertSellerCommand.getSellerId());
        sellerRepository.save(seller);
    }
}
