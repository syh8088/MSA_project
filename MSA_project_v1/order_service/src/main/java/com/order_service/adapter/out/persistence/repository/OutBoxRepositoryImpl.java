package com.order_service.adapter.out.persistence.repository;

import com.order_service.adapter.out.persistence.entity.QOutBox;
import com.order_service.domain.OutBoxOutPut;
import com.order_service.domain.OutBoxStatus;
import com.order_service.domain.QOutBoxOutPut;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class OutBoxRepositoryImpl implements OutBoxRepositoryCustom {

	QOutBox qOutBox = QOutBox.outBox;

	private final JPAQueryFactory queryFactory;

	public OutBoxRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<OutBoxOutPut> selectOutBoxPendingEventMessageList(OutBoxStatus outBoxStatus) {
		return queryFactory
				.select(
						new QOutBoxOutPut(
								qOutBox.no,
								qOutBox.status,
								qOutBox.idempotencyKey,
								qOutBox.type,
								qOutBox.partitionKey,
								qOutBox.payload,
								qOutBox.metadata
						)
				)
				.from(qOutBox)
				.where(qOutBox.status.eq(outBoxStatus))
				.fetch();
	}
}



