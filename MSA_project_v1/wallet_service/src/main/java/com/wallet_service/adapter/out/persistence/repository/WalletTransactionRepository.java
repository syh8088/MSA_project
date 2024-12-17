package com.wallet_service.adapter.out.persistence.repository;

import com.wallet_service.adapter.out.persistence.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long>, WalletTransactionRepositoryCustom {

    boolean existsByOrderId(String orderId);
}