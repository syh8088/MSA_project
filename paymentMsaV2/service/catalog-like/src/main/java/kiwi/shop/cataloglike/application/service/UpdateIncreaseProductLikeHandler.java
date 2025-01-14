package kiwi.shop.cataloglike.application.service;

import kiwi.shop.cataloglike.application.port.out.ProductLikeCountPort;
import kiwi.shop.cataloglike.application.port.out.ProductLikePort;
import kiwi.shop.cataloglike.common.UseCase;
import kiwi.shop.cataloglike.domain.ProductLikeCommand;
import kiwi.shop.cataloglike.domain.UpdateProductLikeCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class UpdateIncreaseProductLikeHandler implements UpdateProductLikeHandler {

    private final ProductLikePort productLikePort;
    private final ProductLikeCountPort productLikeCountPort;

    private final TransactionTemplate transactionTemplate;

    @Override
    public void execute(long productNo, UpdateProductLikeCommand updateProductLikeCommand) {

        ProductLikeCommand productLikeCommand = (ProductLikeCommand) updateProductLikeCommand;

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                productLikePort.like(productLikeCommand);
                productLikeCountPort.increase(productNo);
            }
        });
    }

    @Override
    public boolean supports(UpdateProductLikeCommand productLikeCommand) {

        return productLikeCommand instanceof ProductLikeCommand;
    }
}
