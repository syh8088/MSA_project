package kiwi.shop.seller.adapter.out.persistence.service;

import kiwi.shop.seller.adapter.out.persistence.entity.Seller;
import kiwi.shop.seller.adapter.out.persistence.repository.SellerRepository;
import kiwi.shop.seller.application.port.out.DeleteSellerPort;
import kiwi.shop.seller.application.port.out.InsertSellerPort;
import kiwi.shop.seller.common.WebAdapter;
import kiwi.shop.seller.domain.RequestPersistenceInsertSellerCommand;
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

        return savedSeller.getSellerNo();
    }

    @Transactional
    @Override
    public void deleteSeller(long sellerNo) {

        sellerRepository.updateSellerIsDeletedBySellerNo(sellerNo, true);
    }
}
