package com.order_service.adapter.out.persistence.repository;

import com.order_service.adapter.out.persistence.entity.PaymentOrder;
import com.order_service.domain.PaymentOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long>, PaymentOrderRepositoryCustom {

    @Modifying
    @Query("UPDATE PaymentOrder AS p SET p.status = :status, p.updatedDateTime = CURRENT_TIMESTAMP WHERE p.orderId = :orderId")
    void updatePaymentOrderStatusByOrderId(@Param("orderId") String orderId, @Param("status") PaymentOrderStatus status);

    @Query("select sum(p.amount) from PaymentOrder AS p where p.orderId = :orderId")
    BigDecimal selectTotalAmountByOrderId(@Param("orderId") String orderId);
}