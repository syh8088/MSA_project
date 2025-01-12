package kiwi.shop.orderquery.application.service.event;

import kiwi.shop.common.event.Event;
import kiwi.shop.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {

    void handle(Event<T> event);

    boolean supports(Event<T> event);
}
