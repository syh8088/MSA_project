package com.seller_service.adapter.out.persistence.service;

import com.common.WebAdapter;
import com.seller_service.adapter.out.persistence.entity.Seller;
import com.seller_service.adapter.out.persistence.repository.SellerRepository;
import com.seller_service.application.port.out.DeleteSellerPort;
import com.seller_service.application.port.out.InsertSellerPort;
import com.seller_service.domain.RequestPersistenceInsertSellerCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class SellerPersistenceAdapter implements InsertSellerPort, DeleteSellerPort {

    private final SellerRepository sellerRepository;

    @Override
    public long insertSeller(RequestPersistenceInsertSellerCommand insertSellerCommand) {

        Seller seller = Seller.of(insertSellerCommand.getSellerId());
        Seller savedSeller = sellerRepository.save(seller);

        return savedSeller.getNo();
    }

    @Transactional
    @Override
    public void deleteSeller(long sellerNo) {

        sellerRepository.updateSellerIsDeletedBySellerNo(sellerNo, true);
    }
}
