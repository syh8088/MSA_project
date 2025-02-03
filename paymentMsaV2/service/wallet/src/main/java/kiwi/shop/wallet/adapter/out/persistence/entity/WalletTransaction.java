package kiwi.shop.wallet.adapter.out.persistence.entity;

import kiwi.shop.common.snowflake.Snowflake;
import kiwi.shop.wallet.domain.enums.TransactionType;
import kiwi.shop.wallet.domain.WalletTransactionOutPut;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "wallet_transactions")
public class WalletTransaction {

    @Id
    @Column(name = "wallet_transaction_no")
    private Long walletTransactionNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_no")
    private Wallet wallet;

    @Column
    private BigDecimal amount;

    @Enumerated(value = EnumType.STRING)
    private TransactionType type;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "reference_type")
    private String referenceType;

    @Column(name = "reference_id")
    private Long referenceId;

    @Column(name = "idempotency_key")
    private String idempotencyKey;

    private WalletTransaction(Long walletTransactionNo, Wallet wallet, BigDecimal amount, TransactionType type, String referenceType, long referenceId, String orderId, String idempotencyKey) {
        this.walletTransactionNo = walletTransactionNo;
        this.wallet = wallet;
        this.amount = amount;
        this.type = type;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.orderId = orderId;
        this.idempotencyKey = idempotencyKey;
    }

    public static WalletTransaction of(Wallet wallet, BigDecimal amount, TransactionType type, String referenceType, long referenceId, String orderId, String idempotencyKey) {
        Snowflake snowflake = new Snowflake();
        return new WalletTransaction(snowflake.nextId(), wallet, amount, type, referenceType, referenceId, orderId, idempotencyKey);
    }

    public static WalletTransaction of(long walletNo, BigDecimal amount, TransactionType type, String referenceType, long referenceId, String orderId, String idempotencyKey) {
        Snowflake snowflake = new Snowflake();
        return new WalletTransaction(snowflake.nextId(), Wallet.of(walletNo), amount, type, referenceType, referenceId, orderId, idempotencyKey);
    }

    public static List<WalletTransaction> of(long walletNo, List<WalletTransactionOutPut> walletTransactionList) {
        return walletTransactionList.stream()
                .map(data -> WalletTransaction.of(
                        walletNo,
                        data.getAmount(),
                        data.getType(),
                        data.getReferenceType(),
                        data.getReferenceId(),
                        data.getOrderId(),
                        data.getIdempotencyKey()
                ))
                .toList();
    }
}