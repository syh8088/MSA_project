package com.order_query_service.adapter.in.stream;

import com.common.StreamAdapter;
import com.order_query_service.application.port.in.OrderQuerySellerAmountSumGroupingProductUseCase;
import com.order_query_service.application.port.out.GetPaymentOrderPort;
import com.order_query_service.application.port.out.PaymentOrderRedisPort;
import com.order_query_service.domain.PaymentOrderWithSellerOutPut;
import com.order_query_service.domain.message.OrderQueryEventMessage;
import com.order_query_service.domain.message.OrderQueryEventMessageType;
import com.order_query_service.domain.message.PaymentEventMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.DefaultKafkaHeaderMapper;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.converter.MessagingMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Configuration
@StreamAdapter
@RequiredArgsConstructor
public class PaymentEventMessageHandler {

	private final PaymentOrderRedisPort paymentOrderRedisPort;
	private final GetPaymentOrderPort getPaymentOrderPort;

	private final OrderQuerySellerAmountSumGroupingProductUseCase orderQuerySellerAmountSumGroupingProductUseCase;

	private final StreamBridge streamBridge;

	@Bean
	public MessagingMessageConverter converter() {

		MessagingMessageConverter converter = new MessagingMessageConverter();
		DefaultKafkaHeaderMapper mapper = new DefaultKafkaHeaderMapper();
		mapper.setMapAllStringsOut(false);
		mapper.setEncodeStrings(true);
		converter.setHeaderMapper(mapper);

		return converter;
	}

	@Bean
	public Consumer<Message<PaymentEventMessage>> consume() {
		return message -> {

			log.info("key: {}", message.getHeaders().get(KafkaHeaders.RECEIVED_KEY));
			PaymentEventMessage paymentEventMessage = message.getPayload();
			log.info("payload: {}", paymentEventMessage);

			String orderId = paymentEventMessage.orderId();
			log.info("consume orderId: {}", orderId);

			orderQuerySellerAmountSumGroupingProductUseCase.updateSellerAmountSum(orderId);

			OrderQueryEventMessage orderQueryEventMessage = this.createOrderQueryEventMessage(orderId);
			streamBridge.send("orderquery", MessageBuilder.withPayload(orderQueryEventMessage).build());
		};
	}

	private OrderQueryEventMessage createOrderQueryEventMessage(String orderId) {
		return OrderQueryEventMessage.of(
				OrderQueryEventMessageType.SUCCESS,
				Map.of("orderId", orderId)
		);
	}
}
