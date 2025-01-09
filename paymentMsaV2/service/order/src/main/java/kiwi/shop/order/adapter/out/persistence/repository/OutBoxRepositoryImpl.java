package kiwi.shop.order.adapter.out.persistence.repository;

import kiwi.shop.order.adapter.out.persistence.entity.QOutBox;
import kiwi.shop.order.domain.OutBoxOutPut;
import kiwi.shop.order.domain.OutBoxStatus;
import kiwi.shop.order.domain.QOutBoxOutPut;
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



