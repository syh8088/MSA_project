package kiwi.shop.wallet.adapter.in.stream;

import kiwi.shop.common.event.Event;
import kiwi.shop.common.event.EventType;
import kiwi.shop.common.event.payload.PaymentConfirmEventPayload;
import kiwi.shop.wallet.application.port.in.SettlementUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventMessageHandler implements EventHandler<PaymentConfirmEventPayload> {

    private final SettlementUseCase settlementUseCase;

    @Override
    public void handle(Event<PaymentConfirmEventPayload> event) {

        PaymentConfirmEventPayload payload = event.getPayload();
        settlementUseCase.settlementProcess(payload.getOrderId());
    }

    @Override
    public boolean supports(Event<PaymentConfirmEventPayload> event) {
        return EventType.PAYMENT_CONFIRM == event.getType();
    }
}
