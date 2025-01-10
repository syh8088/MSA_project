package kiwi.shop.wallet.adapter.out.persistence.service;

import kiwi.shop.wallet.adapter.out.persistence.entity.Wallet;
import kiwi.shop.wallet.adapter.out.persistence.entity.WalletTransaction;
import kiwi.shop.wallet.adapter.out.persistence.repository.WalletRepository;
import kiwi.shop.wallet.adapter.out.persistence.repository.WalletTransactionRepository;
import kiwi.shop.wallet.application.port.out.GetWalletPort;
import kiwi.shop.wallet.application.port.out.InsertWalletPort;
import kiwi.shop.wallet.application.port.out.InsertWalletTransactionsPort;
import kiwi.shop.wallet.application.port.out.UpdateWalletPort;
import kiwi.shop.wallet.common.WebAdapter;
import kiwi.shop.wallet.domain.InsertWalletCommand;
import kiwi.shop.wallet.domain.WalletOutPut;
import kiwi.shop.wallet.domain.WalletTransactionOutPut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class WalletPersistenceAdapter implements GetWalletPort, UpdateWalletPort, InsertWalletTransactionsPort, InsertWalletPort {

    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    @Override
    public boolean isAlreadyProcessWallet(String orderId) {

        return walletTransactionRepository.existsByOrderId(orderId);
    }

    @Override
    public List<WalletOutPut> selectWalletListBySellerNoList(List<Long> sellerNoList) {
        return walletRepository.selectWalletListBySellerNoList(sellerNoList);
    }

    @Override
    public List<WalletOutPut> selectWalletListWithWalletTransactionsByWalletNoList(List<Long> walletNoList) {
        return walletRepository.selectWalletListByWalletNoList(walletNoList);
    }

    @Transactional
    @Override
    public void updateWalletList(List<WalletOutPut> updatedWallets) {
        List<Wallet> wallets = Wallet.of(updatedWallets);
        walletRepository.saveAll(wallets);
    }

    @Transactional
    @Override
    public void insertWalletTransactionList(List<WalletOutPut> updatedWallets) {

        List<WalletTransaction> walletTransactions = updatedWallets.stream()
                .map(data -> {
                    List<WalletTransactionOutPut> walletTransactionList = data.getWalletTransactionList();
                    return WalletTransaction.of(data.getWalletNo(), walletTransactionList);
                })
                .flatMap(List::stream)
                .toList();

        walletTransactionRepository.saveAll(walletTransactions);
    }

    @Transactional
    @Override
    public boolean insertWallet(InsertWalletCommand insertWalletCommand) {

        try {
            Wallet wallet = Wallet.of(insertWalletCommand.getSellerNo(), BigDecimal.ZERO);
            walletRepository.save(wallet);

            return true;
        }
        catch (Exception e) {
            return false;
        }

    }
}
