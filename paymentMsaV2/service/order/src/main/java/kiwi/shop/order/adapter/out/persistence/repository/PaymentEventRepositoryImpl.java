package kiwi.shop.order.adapter.out.persistence.repository;

import kiwi.shop.common.event.domain.QPaymentEventWithOrderOutPut;
import kiwi.shop.order.adapter.out.persistence.entity.QPaymentEvent;
import kiwi.shop.order.adapter.out.persistence.entity.QPaymentOrder;
import kiwi.shop.order.domain.PaymentEventOutPut;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kiwi.shop.common.event.domain.PaymentEventWithOrderOutPut;
import kiwi.shop.order.domain.QPaymentEventOutPut;

import java.util.List;
import java.util.Optional;

public class PaymentEventRepositoryImpl implements PaymentEventRepositoryCustom {

	QPaymentEvent qPaymentEvent = QPaymentEvent.paymentEvent;
	QPaymentOrder qPaymentOrder = QPaymentOrder.paymentOrder;

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
								qPaymentEvent.memberNo,
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

	@Override
	public List<PaymentEventOutPut> selectPaymentEventListByMemberNo(long memberNo) {
		return queryFactory
				.select(
						new QPaymentEventOutPut(
								qPaymentEvent.paymentEventNo,
								qPaymentEvent.memberNo,
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
				.where(qPaymentEvent.memberNo.eq(memberNo))
				.fetch();
	}

	@Override
	public Optional<PaymentEventOutPut> selectPaymentEventByMemberNoAndPaymentMethodNo(long memberNo, long paymentMethodNo) {
		return Optional.ofNullable(queryFactory
                .select(
                        new QPaymentEventOutPut(
                                qPaymentEvent.paymentEventNo,
                                qPaymentEvent.memberNo,
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
                .where(qPaymentEvent.paymentEventNo.eq(paymentMethodNo).and(qPaymentEvent.memberNo.eq(memberNo)))
                .fetchOne());
	}

	@Override
	public List<PaymentEventWithOrderOutPut> selectPaymentEventWithOrderListByMemberNoAndPaymentEventNo(long memberNo, long paymentEventNo) {
		return queryFactory
				.select(
						new QPaymentEventWithOrderOutPut(
								qPaymentEvent.paymentEventNo,
								qPaymentEvent.memberNo,
								qPaymentEvent.orderId,
								qPaymentEvent.paymentKey,
								qPaymentEvent.orderName,
								qPaymentEvent.method,
								qPaymentEvent.type,
								qPaymentEvent.approvedDateTime,
								qPaymentEvent.isPaymentDone,
								qPaymentOrder.paymentOrderNo,
								qPaymentOrder.productNo,
								qPaymentOrder.sellerNo,
								qPaymentOrder.amount,
								qPaymentOrder.status,
								qPaymentOrder.productName
						)
				)
				.from(qPaymentEvent)
				.innerJoin(qPaymentOrder)
				.on(qPaymentOrder.paymentEvent.eq(qPaymentEvent))
				.where(qPaymentEvent.paymentEventNo.eq(paymentEventNo).and(qPaymentEvent.memberNo.eq(memberNo)))
				.fetch();
	}

	@Override
	public List<PaymentEventWithOrderOutPut> selectPaymentEventWithOrderListByOrderId(String orderId) {
		return queryFactory
				.select(
						new QPaymentEventWithOrderOutPut(
								qPaymentEvent.paymentEventNo,
								qPaymentEvent.memberNo,
								qPaymentEvent.orderId,
								qPaymentEvent.paymentKey,
								qPaymentEvent.orderName,
								qPaymentEvent.method,
								qPaymentEvent.type,
								qPaymentEvent.approvedDateTime,
								qPaymentEvent.isPaymentDone,
								qPaymentOrder.paymentOrderNo,
								qPaymentOrder.productNo,
								qPaymentOrder.sellerNo,
								qPaymentOrder.amount,
								qPaymentOrder.status,
								qPaymentOrder.productName
						)
				)
				.from(qPaymentEvent)
				.innerJoin(qPaymentOrder)
				.on(qPaymentOrder.paymentEvent.eq(qPaymentEvent))
				.where(qPaymentEvent.orderId.eq(orderId))
				.fetch();
	}

}



