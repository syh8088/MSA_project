package kiwi.shop.hotcatalog.application.port.out;

public interface HotCatalogScoreCalculatorPort {

    long calculateHotCatalogScore(long productNo);
}
