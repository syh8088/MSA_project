package kiwi.shop.catalogreview.adapter.out.persistence.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kiwi.shop.catalogreview.adapter.out.persistence.entity.QCatalogReviewCount;
import kiwi.shop.catalogreview.domain.CatalogReviewCountOutPut;
import kiwi.shop.catalogreview.domain.QCatalogReviewCountOutPut;

public class CatalogReviewCountRepositoryImpl implements CatalogReviewCountRepositoryCustom {

	QCatalogReviewCount qCatalogReviewCount = QCatalogReviewCount.catalogReviewCount;

	private final JPAQueryFactory queryFactory;

	public CatalogReviewCountRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public CatalogReviewCountOutPut selectCatalogReviewCountByProductNo(long productNo) {
		return queryFactory
				.select(
						new QCatalogReviewCountOutPut(
								qCatalogReviewCount.productNo,
								qCatalogReviewCount.reviewCount,
								qCatalogReviewCount.reviewStarRatingSum
						)
				)
				.from(qCatalogReviewCount)
				.where(qCatalogReviewCount.productNo.eq(productNo))
				.fetchOne();
	}
}



