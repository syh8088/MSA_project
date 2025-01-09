package kiwi.shop.order.domain.message;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class PaymentApplicationEventListener {

    private final StreamBridge streamBridge;
    private static final String bindingName = "send-out-0";

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void transactionalEventListenerAfterCommit(PaymentEventMessage paymentEventMessage) {

        if (!Objects.isNull(paymentEventMessage)) {
            streamBridge.send(bindingName, MessageBuilder
                    .withPayload(paymentEventMessage)
                    .setHeader(KafkaHeaders.KEY, paymentEventMessage.getMetadata().get("partitionKey"))
                    .build()
            );
        }
    }
}
