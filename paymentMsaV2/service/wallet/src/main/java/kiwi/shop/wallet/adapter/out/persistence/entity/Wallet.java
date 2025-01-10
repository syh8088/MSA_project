package kiwi.shop.wallet.adapter.out.persistence.entity;

import kiwi.shop.wallet.domain.WalletOutPut;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "wallets")
public class Wallet {

    @Id
    @Column(name = "wallet_no")
    private Long walletNo;

    @Column(name = "seller_no")
    private long sellerNo;

    private BigDecimal balance;

    @Version
    private int version;

    @Builder
    private Wallet(Long walletNo, long sellerNo, BigDecimal balance, int version) {
        this.walletNo = walletNo;
        this.sellerNo = sellerNo;
        this.balance = balance;
        this.version = version;
    }

    public static Wallet of(long walletNo) {
        return Wallet.builder()
                .walletNo(walletNo)
                .build();
    }

    public static Wallet of(long sellerNo, BigDecimal balance) {
        return Wallet.builder()
                .sellerNo(sellerNo)
                .balance(balance)
                .build();
    }

    public static Wallet of(WalletOutPut walletOutPut) {
        return Wallet.builder()
                .walletNo(walletOutPut.getWalletNo())
                .sellerNo(walletOutPut.getSellerNo())
                .balance(walletOutPut.getBalance())
                .version(walletOutPut.getVersion())
                .build();
    }

    public static List<Wallet> of(List<WalletOutPut> walletOutPutList) {
        return walletOutPutList.stream()
                .map(Wallet::of)
                .toList();
    }
}