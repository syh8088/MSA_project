package kiwi.shop.cataloglike.application.service;

import kiwi.shop.cataloglike.domain.UpdateProductLikeCommand;

import java.util.List;

public interface UpdateProductLikeHandler {

    void execute(long productNo, UpdateProductLikeCommand updateProductLikeCommand);

    boolean supports(UpdateProductLikeCommand productLikeCommand);

    static UpdateProductLikeHandler getHandlerUpdateProductLikeServices(
            UpdateProductLikeCommand productLikeCommand,
            List<UpdateProductLikeHandler> updateProductLikeHandlers
    ) {

        for (UpdateProductLikeHandler updateProductLikeHandler : updateProductLikeHandlers) {
            if (updateProductLikeHandler.supports(productLikeCommand)) {
                return updateProductLikeHandler;
            }
        }

        throw new IllegalArgumentException("handler getHandlerUpdateProductLikeServices 를 찾을 수 없습니다.");
    }

}
