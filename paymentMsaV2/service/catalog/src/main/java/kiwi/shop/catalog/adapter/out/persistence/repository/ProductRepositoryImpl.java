package kiwi.shop.catalog.adapter.out.persistence.repository;

import kiwi.shop.catalog.adapter.out.persistence.entity.QProduct;
import kiwi.shop.catalog.domain.ProductOutPut;
import kiwi.shop.catalog.domain.QProductOutPut;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

	QProduct qProduct = QProduct.product;

	private final JPAQueryFactory queryFactory;

	public ProductRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<ProductOutPut> selectProductListByProductNoList(List<Long> productNoList) {
		return queryFactory
				.select(
						new QProductOutPut(
								qProduct.productNo,
								qProduct.sellerNo,
								qProduct.productId,
								qProduct.name,
								qProduct.price
						)
				)
				.from(qProduct)
				.where(qProduct.productNo.in(productNoList))
				.fetch();
	}
}



