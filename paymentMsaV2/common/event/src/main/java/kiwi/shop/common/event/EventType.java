package kiwi.shop.common.event;

import kiwi.shop.common.event.payload.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {

    PAYMENT_CONFIRM(PaymentConfirmEventPayload.class, Topic.KIWI_SHOP_PAYMENT),
    PRODUCT_LIKED(ProductLikedEventPayload.class, Topic.KIWI_SHOP_PRODUCT_LIKE),
    PRODUCT_UNLIKED(ProductUnLikedEventPayload.class, Topic.KIWI_SHOP_PRODUCT_LIKE),
    PRODUCT_CREATED_REVIEW(ReviewCreatedEventPayload.class, Topic.KIWI_SHOP_PRODUCT_REVIEW),
    PRODUCT_DELETED_REVIEW(ReviewDeletedEventPayload.class, Topic.KIWI_SHOP_PRODUCT_REVIEW),
//    WALLET(PaymentConfirmEventPayload.class, Topic.KIWI_SHOP_WALLET),
//    ORDER_QUERY(PaymentConfirmEventPayload.class, Topic.KIWI_SHOP_ORDERQUERY),


    ;

    private final Class<? extends EventPayload> payloadClass;
    private final String topic;

    public static EventType from(String type) {
        try {
            return valueOf(type);
        }
        catch (Exception e) {
            log.error("[EventType.from] type={}", type, e);
            return null;
        }
    }

    public static class Topic {
        public static final String KIWI_SHOP_PAYMENT = "kiwi-shop-payment";
        public static final String KIWI_SHOP_PRODUCT_LIKE = "kiwi-shop-product-like";
        public static final String KIWI_SHOP_PRODUCT_REVIEW = "kiwi-shop-product-review";
        public static final String KIWI_SHOP_WALLET = "kiwi-shop-wallet";
        public static final String KIWI_SHOP_ORDERQUERY = "kiwi-shop-orderquery";
    }
}
