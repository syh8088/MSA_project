package kiwi.shop.hotcatalog.application.service;

import kiwi.shop.common.event.Event;
import kiwi.shop.common.event.EventPayload;
import kiwi.shop.hotcatalog.application.port.in.HotCatalogUseCase;
import kiwi.shop.hotcatalog.application.port.out.HotProductListPort;
import kiwi.shop.hotcatalog.application.port.out.HotCatalogScoreCalculatorPort;
import kiwi.shop.hotcatalog.application.service.handler.EventHandler;
import kiwi.shop.hotcatalog.common.UseCase;
import kiwi.shop.hotcatalog.domain.SelectProductResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class HotProductService implements HotCatalogUseCase {

    private final HotCatalogScoreCalculatorPort hotCatalogScoreCalculatorPort;
    private final HotProductListPort hotProductListPort;

    private final List<EventHandler> eventHandlers;

    // 인기 상품은 최대 10개 까지만 저장 하도록 합니다.
    private static final long HOT_PRODUCT_LIMIT_COUNT = 10;

    // 최근 10일까지만 저장 하도록 합니다.
    private static final Duration HOT_PRODUCT_EXPIRE_TTL = Duration.ofDays(10);


    @Override
    public Optional<SelectProductResponses> selectHotProductList() {

        return hotProductListPort.selectHotProductList();
    }

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

        eventHandler.handle(event);

        /**
         * 해당 상품에 대한 인기글 점수 계산을 합니다.
         */
        long score = hotCatalogScoreCalculatorPort.calculateHotCatalogScore(productNo);
        hotProductListPort.registerHotProduct(
                productNo,
                score,
                HOT_PRODUCT_LIMIT_COUNT,
                HOT_PRODUCT_EXPIRE_TTL
        );
    }

    private EventHandler<EventPayload> extectEventHandler(Event<EventPayload> event) {

        return eventHandlers.stream()
                .filter(eventHandler -> eventHandler.supports(event))
                .findAny()
                .orElse(null);
    }
}
