package kiwi.shop.hotcatalog.application.port.out;

import java.time.Duration;

public interface HotCatalogListPort {

    void registerHotCatalog(long productNo, long score, long hotProductLimitCount, Duration hotProductExpireTtl);
}