package kiwi.shop.catalogreview.application.service;

import kiwi.shop.catalogreview.application.port.out.ProductReviewCountPort;
import kiwi.shop.catalogreview.common.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class DecreaseProductReviewHandler implements UpdateProductReviewHandler {

    private final ProductReviewCountPort productReviewCountPort;

    private final TransactionTemplate transactionTemplate;

    @Override
    public void execute(long productNo, long starRating) {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                productReviewCountPort.decrease(productNo, starRating);
            }
        });
    }

    @Override
    public boolean supports(UpdateType updateType) {

        return updateType == UpdateType.DELETE;
    }
}
