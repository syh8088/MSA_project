package kiwi.shop.catalog.common;

import java.util.UUID;

/**
 * 멱등성 키 만드는 멱등성 크리에이터
 */
public class IdempotencyCreator {

    public static String create(Object data) {
        return UUID.nameUUIDFromBytes(data.toString().getBytes()).toString();
    }
}