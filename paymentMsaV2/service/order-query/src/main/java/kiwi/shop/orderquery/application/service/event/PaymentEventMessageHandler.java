package kiwi.shop.orderquery.application.service.event;

import kiwi.shop.common.event.Event;
import kiwi.shop.common.event.EventType;
import kiwi.shop.common.event.payload.PaymentConfirmEventPayload;
import kiwi.shop.orderquery.application.port.in.PaymentQueryUseCase;
import kiwi.shop.orderquery.domain.PaymentEventQueryModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventMessageHandler implements EventHandler<PaymentConfirmEventPayload> {

    private final PaymentQueryUseCase paymentQueryUseCase;

    @Override
    public void handle(Event<PaymentConfirmEventPayload> event) {

        PaymentConfirmEventPayload payload = event.getPayload();
        Optional<PaymentEventQueryModel> paymentEventQueryModel = PaymentEventQueryModel.of(payload);
        paymentEventQueryModel.ifPresent(paymentQueryUseCase::insertOrderQuery);
    }

    @Override
    public boolean supports(Event<PaymentConfirmEventPayload> event) {
        return EventType.PAYMENT_CONFIRM == event.getType();
    }
}
