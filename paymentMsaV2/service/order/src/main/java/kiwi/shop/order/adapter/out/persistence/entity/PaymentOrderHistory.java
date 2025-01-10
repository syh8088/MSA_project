package kiwi.shop.order.adapter.out.persistence.entity;

import kiwi.shop.common.snowflake.Snowflake;
import kiwi.shop.order.application.port.out.PaymentOrderStatusOutPut;
import kiwi.shop.order.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payment_order_histories")
public class PaymentOrderHistory extends CommonEntity {

    @Id
    @Column(name = "payment_order_history_no")
    private Long paymentOrderHistoryNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_order_no")
    private PaymentOrder paymentOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status")
    private PaymentOrderStatus previousStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status")
    private PaymentOrderStatus newStatus;

    @Column(name = "reason")
    private String reason;

    @Builder
    private PaymentOrderHistory(Long paymentOrderHistoryNo, PaymentOrder paymentOrder, PaymentOrderStatus previousStatus, PaymentOrderStatus newStatus, String reason) {
        this.paymentOrderHistoryNo = paymentOrderHistoryNo;
        this.paymentOrder = paymentOrder;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.reason = reason;
    }

    public static PaymentOrderHistory of(
            long paymentOrderNo,
            PaymentOrderStatus previousStatus,
            PaymentOrderStatus newStatus,
            String reason
    ) {

        Snowflake snowflake = new Snowflake();

        return PaymentOrderHistory.builder()
                .paymentOrderHistoryNo(snowflake.nextId())
                .paymentOrder(PaymentOrder.of(paymentOrderNo))
                .previousStatus(previousStatus)
                .newStatus(newStatus)
                .reason(reason)
                .build();
    }

    public static List<PaymentOrderHistory> of(
            List<PaymentOrderStatusOutPut> paymentOrderStatusList,
            PaymentOrderStatus newPaymentStatus,
            String reason
    ) {
        return paymentOrderStatusList.stream()
                .map(data -> PaymentOrderHistory.of(data.getPaymentOrderNo(), data.getStatus(), newPaymentStatus, reason))
                .toList();
    }

}