package kiwi.shop.cataloglike.application.service;

import kiwi.shop.cataloglike.application.port.out.ProductLikeCountPort;
import kiwi.shop.cataloglike.common.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class DecreaseProductLikeHandler implements UpdateProductLikeHandler {

    private final ProductLikeCountPort productLikeCountPort;

    private final TransactionTemplate transactionTemplate;

    @Override
    public void execute(long productNo) {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                productLikeCountPort.decrease(productNo);
            }
        });
    }

    @Override
    public boolean supports(UpdateType updateType) {

        return updateType == UpdateType.UNLIKE;
    }
}
