package kiwi.shop.wallet.adapter.in.stream;

import kiwi.shop.common.event.Event;
import kiwi.shop.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {

    void handle(Event<T> event);

    boolean supports(Event<T> event);
}
