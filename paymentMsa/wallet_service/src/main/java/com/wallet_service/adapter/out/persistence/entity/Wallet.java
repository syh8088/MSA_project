package com.wallet_service.adapter.out.persistence.entity;

import com.wallet_service.domain.WalletOutPut;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(name = "seller_no")
    private long sellerNo;

    private BigDecimal balance;

    @Version
    private int version;

    @Builder
    private Wallet(Long no, long sellerNo, BigDecimal balance, int version) {
        this.no = no;
        this.sellerNo = sellerNo;
        this.balance = balance;
        this.version = version;
    }

    public static Wallet of(long walletNo) {
        return Wallet.builder()
                .no(walletNo)
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
                .no(walletOutPut.getWalletNo())
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