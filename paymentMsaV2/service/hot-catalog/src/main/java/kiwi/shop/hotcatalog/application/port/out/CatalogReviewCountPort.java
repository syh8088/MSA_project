package kiwi.shop.hotcatalog.application.port.out;

import java.time.Duration;

public interface CatalogReviewCountPort {

    void updateCatalogReviewCount(
            long productReviewNo,
            long productNo,
            long memberNo,
            String title,
            String content,
            long starRating,
            Boolean isDeleted,
            long reviewCount,
            long reviewStarRatingSum,
            Duration duration
    );
}
