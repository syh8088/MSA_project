package kiwi.shop.wallet.adapter.out.persistence.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kiwi.shop.wallet.adapter.out.persistence.entity.QWallet;
import kiwi.shop.wallet.domain.QWalletOutPut;
import kiwi.shop.wallet.domain.WalletOutPut;
import jakarta.persistence.EntityManager;

import java.util.List;

public class WalletRepositoryImpl implements WalletRepositoryCustom {

	QWallet qWallet = QWallet.wallet;
//	QSeller qSeller = QSeller.seller;

	private final JPAQueryFactory queryFactory;

	public WalletRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<WalletOutPut> selectWalletListBySellerNoList(List<Long> sellerNoList) {
		return queryFactory
				.select(
						new QWalletOutPut(
								qWallet.walletNo,
								qWallet.sellerNo,
								qWallet.balance,
								qWallet.version
						)
				)
				.from(qWallet)
				.where(qWallet.sellerNo.in(sellerNoList))
				.fetch();
	}

	@Override
	public List<WalletOutPut> selectWalletListByWalletNoList(List<Long> walletNoList) {
		return queryFactory
				.select(
						new QWalletOutPut(
								qWallet.walletNo,
								qWallet.sellerNo,
								qWallet.balance,
								qWallet.version
						)
				)
				.from(qWallet)
				.where(qWallet.walletNo.in(walletNoList))
				.fetch();
	}
}



