package kiwi.shop.hotcatalog.application.service;

import kiwi.shop.common.event.Event;
import kiwi.shop.common.event.EventPayload;
import kiwi.shop.hotcatalog.application.port.in.HotCatalogUseCase;
import kiwi.shop.hotcatalog.application.service.handler.EventHandler;
import kiwi.shop.hotcatalog.common.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class HotCatalogService implements HotCatalogUseCase {

    private final List<EventHandler> eventHandlers;

    @Override
    public void messageEventHandler(Event<EventPayload> event) {

        EventHandler<EventPayload> eventHandler = this.extectEventHandler(event);
        if (Objects.isNull(eventHandler)) {
            return;
        }

        /**
         * 이 이벤트가 생성 또는 삭제된 이벤트인지 검사 합니다.
         *
         * 이 경우 이벤트 핸들러 그대로 호출만 하면 됩니다.
         *
         * 그외에는 점수를 업데이트 + 이벤트 핸들러 호출를 해줘야 합니다.
         */
//        if (isProductCreatedOrDeleted(event)) {
//            eventHandler.handle(event);
//        }
//        else {
            this.update(event, eventHandler);
//        }
    }

    private void update(Event<EventPayload> event, EventHandler<EventPayload> eventHandler) {

        long productNo = eventHandler.selectProductNo(event);
//        LocalDateTime createdTime = articleCreatedTimeRepository.read(articleId);

        eventHandler.handle(event);
    }

    private EventHandler<EventPayload> extectEventHandler(Event<EventPayload> event) {

        return eventHandlers.stream()
                .filter(eventHandler -> eventHandler.supports(event))
                .findAny()
                .orElse(null);
    }
}
