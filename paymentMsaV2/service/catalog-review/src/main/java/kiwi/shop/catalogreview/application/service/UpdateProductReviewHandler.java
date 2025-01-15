package kiwi.shop.catalogreview.application.service;

import lombok.Getter;

import java.util.List;

public interface UpdateProductReviewHandler {

    void execute(long productNo, long starRating);

    boolean supports(UpdateType updateType);

    static UpdateProductReviewHandler getHandlerUpdateProductReviewServices(
            UpdateType updateType,
            List<UpdateProductReviewHandler> updateProductReviewHandlers
    ) {

        for (UpdateProductReviewHandler updateProductReviewHandler : updateProductReviewHandlers) {
            if (updateProductReviewHandler.supports(updateType)) {
                return updateProductReviewHandler;
            }
        }

        throw new IllegalArgumentException("handler getHandlerUpdateProductReviewServices 를 찾을 수 없습니다.");
    }

    @Getter
    enum UpdateType {
        INSERT,
        DELETE
    }
}
