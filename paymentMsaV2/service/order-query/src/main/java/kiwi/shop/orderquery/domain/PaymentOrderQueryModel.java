package kiwi.shop.orderquery.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class PaymentOrderQueryModel {

    private long paymentOrderNo;
    private long productNo;
    private BigDecimal amount;
    private PaymentOrderStatus status;
    private String productName;

    @Builder
    private PaymentOrderQueryModel(long paymentOrderNo, long productNo, BigDecimal amount, PaymentOrderStatus status, String productName) {
        this.paymentOrderNo = paymentOrderNo;
        this.productNo = productNo;
        this.amount = amount;
        this.status = status;
        this.productName = productName;
    }

    public static List<PaymentOrderQueryModel> of(List<PaymentOrderResponse> paymentOrderResponseList) {
       return paymentOrderResponseList.stream()
               .map(PaymentOrderQueryModel::of)
               .toList();
    }

    public static PaymentOrderQueryModel of(PaymentOrderResponse paymentOrderResponse) {
        return PaymentOrderQueryModel.builder()
                .paymentOrderNo(paymentOrderResponse.getPaymentOrderNo())
                .productNo(paymentOrderResponse.getProductNo())
                .amount(paymentOrderResponse.getAmount())
                .status(paymentOrderResponse.getStatus())
                .productName(paymentOrderResponse.getProductName())
                .build();
    }
}
