package com.order_service.adapter.out.persistence.repository;

import com.order_service.adapter.out.persistence.entity.QPaymentOrder;
import com.order_service.application.port.out.PaymentOrderStatusOutPut;
import com.order_service.application.port.out.QPaymentOrderStatusOutPut;
import com.order_service.domain.PaymentOrderWithSellerOutPut;
import com.order_service.domain.QPaymentOrderWithSellerOutPut;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PaymentOrderRepositoryImpl implements PaymentOrderRepositoryCustom {

	QPaymentOrder qPaymentOrder = QPaymentOrder.paymentOrder;

	private final JPAQueryFactory queryFactory;

	public PaymentOrderRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<PaymentOrderStatusOutPut> selectPaymentOrderStatusListByOrderId(String orderId) {
		return queryFactory
				.select(
						new QPaymentOrderStatusOutPut(
								qPaymentOrder.no,
								qPaymentOrder.orderId,
								qPaymentOrder.status,
								qPaymentOrder.amount
						)
				)
				.from(qPaymentOrder)
				.where(qPaymentOrder.orderId.eq(orderId))
				.fetch();
	}

	@Override
	public List<PaymentOrderWithSellerOutPut> selectPaymentOrderListWithSellerByOrderId(String orderId) {
		return queryFactory
				.select(
						new QPaymentOrderWithSellerOutPut(
								qPaymentOrder.no,
								qPaymentOrder.orderId,
								qPaymentOrder.amount,
								qPaymentOrder.status,
								qPaymentOrder.sellerNo,
								qPaymentOrder.productNo
						)
				)
				.from(qPaymentOrder)
				.where(qPaymentOrder.orderId.eq(orderId))
				.fetch();
//		return null;
	}

}



