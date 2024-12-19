package com.order_service.adapter.out.persistence.repository;

import com.order_service.adapter.out.persistence.entity.PaymentOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOrderHistoryRepository extends JpaRepository<PaymentOrderHistory, Long> {

}