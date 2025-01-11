package kiwi.shop.catalogreview.adapter.out.persistence.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kiwi.shop.catalogreview.adapter.out.persistence.entity.QProductReviewCount;
import kiwi.shop.catalogreview.domain.ProductReviewCountOutPut;
import kiwi.shop.catalogreview.domain.QProductReviewCountOutPut;

public class ProductReviewCountRepositoryImpl implements ProductReviewCountRepositoryCustom {

	QProductReviewCount qProductReviewCount = QProductReviewCount.productReviewCount;

	private final JPAQueryFactory queryFactory;

	public ProductReviewCountRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public ProductReviewCountOutPut selectCatalogReviewCountByProductNo(long productNo) {
		return queryFactory
				.select(
						new QProductReviewCountOutPut(
								qProductReviewCount.productNo,
								qProductReviewCount.reviewCount,
								qProductReviewCount.reviewStarRatingSum
						)
				)
				.from(qProductReviewCount)
				.where(qProductReviewCount.productNo.eq(productNo))
				.fetchOne();
	}
}



