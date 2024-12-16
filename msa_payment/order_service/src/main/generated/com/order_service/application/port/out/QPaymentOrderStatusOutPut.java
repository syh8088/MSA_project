package com.order_service.application.port.out;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.order_service.application.port.out.QPaymentOrderStatusOutPut is a Querydsl Projection type for PaymentOrderStatusOutPut
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPaymentOrderStatusOutPut extends ConstructorExpression<PaymentOrderStatusOutPut> {

    private static final long serialVersionUID = -704585773L;

    public QPaymentOrderStatusOutPut(com.querydsl.core.types.Expression<Long> no, com.querydsl.core.types.Expression<String> orderId, com.querydsl.core.types.Expression<com.order_service.domain.PaymentOrderStatus> status, com.querydsl.core.types.Expression<? extends java.math.BigDecimal> amount) {
        super(PaymentOrderStatusOutPut.class, new Class<?>[]{long.class, String.class, com.order_service.domain.PaymentOrderStatus.class, java.math.BigDecimal.class}, no, orderId, status, amount);
    }

}

