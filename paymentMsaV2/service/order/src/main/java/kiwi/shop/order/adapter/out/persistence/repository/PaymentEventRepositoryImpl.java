package kiwi.shop.order.adapter.out.persistence.repository;

import kiwi.shop.order.adapter.out.persistence.entity.QPaymentEvent;
import kiwi.shop.order.application.port.out.PaymentEventOutPut;
import kiwi.shop.order.application.port.out.QPaymentEventOutPut;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PaymentEventRepositoryImpl implements PaymentEventRepositoryCustom {

	QPaymentEvent qPaymentEvent = QPaymentEvent.paymentEvent;

	private final JPAQueryFactory queryFactory;

	public PaymentEventRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<PaymentEventOutPut> selectPaymentEventList() {
		return queryFactory
				.select(
						new QPaymentEventOutPut(
								qPaymentEvent.paymentEventNo,
								qPaymentEvent.paymentKey,
								qPaymentEvent.orderId,
								qPaymentEvent.orderName,
								qPaymentEvent.method,
								qPaymentEvent.type,
								qPaymentEvent.approvedDateTime,
								qPaymentEvent.isPaymentDone
						)
				)
				.from(qPaymentEvent)
				.fetch();
	}

}



