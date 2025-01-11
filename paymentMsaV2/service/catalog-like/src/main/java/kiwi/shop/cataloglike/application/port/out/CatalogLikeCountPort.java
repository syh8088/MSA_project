package kiwi.shop.cataloglike.application.port.out;

public interface CatalogLikeCountPort {

    void increase(long productNo);

    void decrease(long productNo);
}
