package kiwi.shop.hotcatalog.application.port.out;

import kiwi.shop.hotcatalog.domain.SelectProductResponses;

import java.time.Duration;
import java.util.Optional;

public interface HotProductListPort {

    void registerHotProduct(long productNo, long score, long hotProductLimitCount, Duration hotProductExpireTtl);

    Optional<SelectProductResponses> selectHotProductList();
}