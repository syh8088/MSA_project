package kiwi.shop.order.adapter.out.persistence.entity;

import kiwi.shop.order.application.port.out.ProductOutPut;
import kiwi.shop.order.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payment_orders")
public class PaymentOrder extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_event_no")
    private PaymentEvent paymentEvent;

    @Column(name = "product_no")
    private long productNo;

    @Column(name = "seller_no")
    private long sellerNo;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "failed_count")
    private int failedCount;

    @Column(name = "threshold")
    private int threshold;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_order_status")
    private PaymentOrderStatus status;

    @Builder
    private PaymentOrder(Long no, PaymentEvent paymentEvent, long productNo, long sellerNo, String orderId, BigDecimal amount, int failedCount, int threshold, PaymentOrderStatus status) {
        this.no = no;
        this.paymentEvent = paymentEvent;
        this.productNo = productNo;
        this.sellerNo = sellerNo;
        this.orderId = orderId;
        this.amount = amount;
        this.failedCount = failedCount;
        this.threshold = threshold;
        this.status = status;
    }

    public static PaymentOrder of(long no) {
        return PaymentOrder.builder()
                .no(no)
                .build();
    }

    public static List<PaymentOrder> of(
            PaymentEvent paymentEvent,
            String orderId,
            PaymentOrderStatus status,
            List<ProductOutPut> productList
    ) {

        return productList.stream()
                .map(productOutPut ->
                        PaymentOrder.of(paymentEvent, orderId, status, productOutPut))
                .toList();
    }

    public static PaymentOrder of(
            PaymentEvent paymentEvent,
            String orderId,
            PaymentOrderStatus status,
            ProductOutPut product
    ) {
        return PaymentOrder.builder()
                .paymentEvent(paymentEvent)
                .orderId(orderId)
                .status(status)
                .amount(product.getPrice())
                .productNo(product.getProductNo())
                .sellerNo(product.getSellerNo())
                .build();
    }

    public static String createPaymentOrderId() {
        return UUID.randomUUID().toString();
    }

}
