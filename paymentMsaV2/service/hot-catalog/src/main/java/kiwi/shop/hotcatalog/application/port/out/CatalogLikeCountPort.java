package kiwi.shop.hotcatalog.application.port.out;

import java.time.Duration;

public interface CatalogLikeCountPort {

    void updateCatalogLikeCount(long productNo, long productLikeCount, Duration duration);
}
