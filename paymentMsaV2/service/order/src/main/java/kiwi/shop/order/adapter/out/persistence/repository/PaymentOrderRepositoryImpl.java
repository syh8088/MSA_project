package kiwi.shop.order.adapter.out.persistence.repository;

import kiwi.shop.order.adapter.out.persistence.entity.QPaymentOrder;
import kiwi.shop.order.application.port.out.PaymentOrderStatusOutPut;
import kiwi.shop.order.application.port.out.QPaymentOrderStatusOutPut;
import kiwi.shop.order.domain.PaymentOrderWithSellerOutPut;
import kiwi.shop.order.domain.QPaymentOrderWithSellerOutPut;
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
								qPaymentOrder.paymentOrderNo,
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
								qPaymentOrder.paymentOrderNo,
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
	}

}



