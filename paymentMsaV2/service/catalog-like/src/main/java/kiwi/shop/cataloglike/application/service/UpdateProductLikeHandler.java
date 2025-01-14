package kiwi.shop.cataloglike.application.service;

import lombok.Getter;

import java.util.List;

public interface UpdateProductLikeHandler {

    void execute(long productNo);

    boolean supports(UpdateType updateType);

    static UpdateProductLikeHandler getHandlerUpdateProductLikeServices(
            UpdateType updateType,
            List<UpdateProductLikeHandler> updateProductLikeHandlers
    ) {

        for (UpdateProductLikeHandler updateProductLikeHandler : updateProductLikeHandlers) {
            if (updateProductLikeHandler.supports(updateType)) {
                return updateProductLikeHandler;
            }
        }

        throw new IllegalArgumentException("handler getHandlerUpdateProductLikeServices 를 찾을 수 없습니다.");
    }

    @Getter
    enum UpdateType {
        LIKE,
        UNLIKE
    }
}
