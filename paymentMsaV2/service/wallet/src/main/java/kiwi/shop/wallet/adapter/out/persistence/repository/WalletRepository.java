package kiwi.shop.wallet.adapter.out.persistence.repository;

import kiwi.shop.wallet.adapter.out.persistence.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long>, WalletRepositoryCustom {

}