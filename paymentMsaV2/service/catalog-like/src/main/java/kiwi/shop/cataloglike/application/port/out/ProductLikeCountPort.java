package kiwi.shop.cataloglike.application.port.out;

public interface ProductLikeCountPort {

    void increase(long productNo);

    void decrease(long productNo);

    long selectProductLikeCountByProductNo(long productNo);

}
