package kiwi.shop.order.adapter.out.persistence.repository;

import kiwi.shop.order.adapter.out.persistence.entity.PaymentOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOrderHistoryRepository extends JpaRepository<PaymentOrderHistory, Long> {

}