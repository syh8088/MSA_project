package kiwi.shop.order.adapter.out.persistence.entity;

import kiwi.shop.common.snowflake.Snowflake;
import kiwi.shop.order.application.port.out.ProductOutPut;
import kiwi.shop.common.event.domain.PaymentEventMethod;
import kiwi.shop.common.event.domain.PaymentEventType;
import kiwi.shop.common.event.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payment_events")
public class PaymentEvent extends CommonEntity {

    @Id
    @Column(name = "payment_event_no")
    private Long paymentEventNo;

    @Column(name = "member_no")
    private Long memberNo;

    @OneToMany(mappedBy = "paymentEvent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PaymentOrder> paymentOrderList = new ArrayList<>();

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "payment_key")
    private String paymentKey;

    @Column(name = "order_name")
    private String orderName;

    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private PaymentEventMethod method;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PaymentEventType type;

    @Column(name = "approved_at")
    private LocalDateTime approvedDateTime;

    @Column(name = "is_payment_done")
    private boolean isPaymentDone;

    @Column(name = "is_wallet_done")
    private boolean isWalletDone;

    @Builder
    private PaymentEvent(Long paymentEventNo, List<PaymentOrder> paymentOrderList, Long memberNo, String orderId, String paymentKey, String orderName, PaymentEventMethod method, PaymentEventType type, LocalDateTime approvedDateTime, boolean isPaymentDone, boolean isWalletDone) {
        this.paymentEventNo = paymentEventNo;
        this.paymentOrderList = paymentOrderList;
        this.memberNo = memberNo;
        this.orderId = orderId;
        this.paymentKey = paymentKey;
        this.orderName = orderName;
        this.method = method;
        this.type = type;
        this.approvedDateTime = approvedDateTime;
        this.isPaymentDone = isPaymentDone;
        this.isWalletDone = isWalletDone;
    }

    public static PaymentEvent of(
            String oderId,
            PaymentEventMethod method,
            PaymentEventType type,
            List<ProductOutPut> productList,
            long memberNo
    ) {

        Snowflake snowflake = new Snowflake();

        PaymentEvent paymentEvent = PaymentEvent.builder()
                .paymentEventNo(snowflake.nextId())
                .orderId(oderId)
                .method(method)
                .type(type)
                .orderName(ProductOutPut.getOrderName(productList))
                .memberNo(memberNo)
                .build();

        List<PaymentOrder> paymentOrders = PaymentOrder.of(
                snowflake,
                paymentEvent,
                oderId,
                PaymentOrderStatus.NOT_STARTED,
                productList
        );

        paymentEvent.updatePaymentOrder(paymentOrders);

        return paymentEvent;
    }

    private void updatePaymentOrder(List<PaymentOrder> paymentOrders) {
        this.paymentOrderList = paymentOrders;
    }

    public BigDecimal getTotalAmount() {
        return this.paymentOrderList
                .stream()
                .map(PaymentOrder::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
