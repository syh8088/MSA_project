package com.wallet_service.adapter.out.persistence.repository;

import com.wallet_service.adapter.out.persistence.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long>, WalletRepositoryCustom {

}