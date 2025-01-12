package kiwi.shop.hotcatalog.application.port.in;

import kiwi.shop.common.event.Event;
import kiwi.shop.common.event.EventPayload;
import kiwi.shop.hotcatalog.domain.SelectProductResponses;

import java.util.Optional;

public interface HotProductUseCase {

    void messageEventHandler(Event<EventPayload> event);

    Optional<SelectProductResponses> selectHotProductList();

}
