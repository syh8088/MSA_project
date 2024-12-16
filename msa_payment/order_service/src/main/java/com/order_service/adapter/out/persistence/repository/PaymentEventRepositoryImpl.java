package com.order_service.adapter.out.persistence.repository;

import com.order_service.application.port.out.PaymentEventOutPut;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PaymentEventRepositoryImpl implements PaymentEventRepositoryCustom {

//	QPaymentEvent qPaymentEvent = QPaymentEvent.paymentEvent;

	private final JPAQueryFactory queryFactory;

	public PaymentEventRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<PaymentEventOutPut> selectPaymentEventList() {
//		return queryFactory
//				.select(
//						new QPaymentEventOutPut(
//								qPaymentEvent.no,
//								qPaymentEvent.paymentKey,
//								qPaymentEvent.orderId,
//								qPaymentEvent.orderName,
//								qPaymentEvent.method,
//								qPaymentEvent.type,
//								qPaymentEvent.approvedDateTime,
//								qPaymentEvent.isPaymentDone
//						)
//				)
//				.from(qPaymentEvent)
//				.fetch();

		return null;
	}

}



