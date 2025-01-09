package kiwi.shop.order.adapter.out.persistence.repository;

import kiwi.shop.order.adapter.out.persistence.entity.PaymentEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PaymentEventRepository extends JpaRepository<PaymentEvent, Long>, PaymentEventRepositoryCustom {

    @Modifying
    @Query("UPDATE PaymentEvent AS p SET p.paymentKey = :paymentKey, p.approvedDateTime = :approvedDateTime, p.isPaymentDone = :isPaymentDone WHERE p.orderId = :orderId")
    void updatePaymentEventExtraDetails(
            @Param("orderId") String orderId,
            @Param("paymentKey") String paymentKey,
            @Param("approvedDateTime") LocalDateTime approvedDateTime,
            @Param("isPaymentDone") boolean isPaymentDone
    );

    @Modifying
    @Query("UPDATE PaymentEvent AS p SET p.isWalletDone = :isWalletDone WHERE p.orderId = :orderId")
    void updateIsWalletDoneByOrderId(@Param("orderId") String orderId, @Param("isWalletDone") boolean isWalletDone);
}