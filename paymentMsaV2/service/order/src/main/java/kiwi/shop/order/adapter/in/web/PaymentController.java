package kiwi.shop.order.adapter.in.web;

import kiwi.shop.order.adapter.out.stream.util.PartitionKeyUtil;
import kiwi.shop.order.application.port.out.PaymentEventOutPut;
import kiwi.shop.order.domain.message.OrderQueryEventMessage;
import kiwi.shop.order.domain.message.OrderQueryEventMessageType;
import kiwi.shop.order.domain.message.PaymentEventMessage;
import kiwi.shop.order.domain.message.PaymentEventMessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {
//
//    private final PaymentApiService paymentApiService;
//
//    @GetMapping
//    public ApiResponse<PaymentEventWithOrderResponse> selectPayments() {
//
//        List<PaymentEventOutPut> paymentEventOutPutList = paymentApiService.selectPayments();
//        List<PaymentEventResponse> paymentEventResponses = PaymentEventResponse.of(paymentEventOutPutList);
//        PaymentEventWithOrderResponse paymentEventWithOrderResponse = PaymentEventWithOrderResponse.of(paymentEventResponses);
//
//        return ApiResponse.ok(paymentEventWithOrderResponse);
//    }
////
//    private final StreamBridge streamBridge;
//    private final PartitionKeyUtil partitionKeyUtil;
//    private static final String bindingName = "send-out-0";
//
//    @GetMapping("test")
//    public String test() {
//
//        String orderId = "tewkgmwepm2394234" + LocalDateTime.now().toString();
//        int partitionKey = partitionKeyUtil.createPartitionKey(orderId.hashCode());
//        PaymentEventMessage paymentEventMessage = this.createPaymentEventMessage(orderId, partitionKey);
//
////        streamBridge.send(bindingName, MessageBuilder.withPayload(paymentEventMessage).build());
////
//        streamBridge.send(bindingName, MessageBuilder
//                .withPayload(paymentEventMessage)
//                .setHeader(KafkaHeaders.KEY, String.valueOf(partitionKey))
//                .build());
//
//        return "test";
//    }
//
//    private PaymentEventMessage createPaymentEventMessage(String orderId, int partitionKey) {
//        return PaymentEventMessage.of(
//                PaymentEventMessageType.PAYMENT_CONFIRMATION_SUCCESS,
//                Map.of("orderId", orderId),
//                Map.of("partitionKey", partitionKey)
//        );
//    }
//
//    @GetMapping("test2")
//    public String test2() {
//
//        String orderId = "tewkgmwepm2394234" + LocalDateTime.now().toString();
//        int partitionKey = partitionKeyUtil.createPartitionKey(orderId.hashCode());
//        OrderQueryEventMessage paymentEventMessage = this.createPaymentEventMessage2(orderId, partitionKey);
//
////        streamBridge.send(bindingName, MessageBuilder.withPayload(paymentEventMessage).build());
////
//        streamBridge.send("order-query-send-out-0", MessageBuilder
//                .withPayload(paymentEventMessage)
//                .setHeader(KafkaHeaders.KEY, String.valueOf(partitionKey))
//                .build());
//
//        return "test";
//    }
//
//    private OrderQueryEventMessage createPaymentEventMessage2(String orderId, int partitionKey) {
//        return OrderQueryEventMessage.of(
//                OrderQueryEventMessageType.SUCCESS,
//                Map.of("orderId", orderId),
//                Map.of("partitionKey", partitionKey)
//        );
//    }
}