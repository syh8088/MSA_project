package kiwi.shop.cataloglike.application.port.in;


public interface CatalogLikeUseCase {


    void like(long productNo, long memberNo);

    void unlike(long productNo, long memberNo);
}
