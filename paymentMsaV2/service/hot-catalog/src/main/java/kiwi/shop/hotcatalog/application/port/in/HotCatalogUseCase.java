package kiwi.shop.hotcatalog.application.port.in;

import kiwi.shop.common.event.Event;
import kiwi.shop.common.event.EventPayload;

public interface HotCatalogUseCase {

    void messageEventHandler(Event<EventPayload> event);
}
