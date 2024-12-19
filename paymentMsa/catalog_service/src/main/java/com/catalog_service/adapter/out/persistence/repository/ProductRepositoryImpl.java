package com.catalog_service.adapter.out.persistence.repository;

import com.catalog_service.adapter.out.persistence.entity.QProduct;
import com.catalog_service.domain.ProductOutPut;
import com.catalog_service.domain.QProductOutPut;
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
								qProduct.no,
								qProduct.sellerNo,
								qProduct.productId,
								qProduct.name,
								qProduct.price
						)
				)
				.from(qProduct)
				.where(qProduct.no.in(productNoList))
				.fetch();
	}
}



