package kiwi.shop.orderquery.client;

import kiwi.shop.orderquery.domain.PaymentEventResponse;
import kiwi.shop.orderquery.domain.PaymentEventResponses;
import kiwi.shop.orderquery.feign.OrderFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@FeignClient(
        name = "order",
        url = "${endpoints.order.url}",
        configuration = {
                OrderFeignConfig.class,
        }
)
public interface OrderClient {

    @GetMapping(value = "/api/orders/members/{memberNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    PaymentEventResponses selectPaymentOrderList(
            @PathVariable("memberNo") long memberNo,
            @RequestParam("paymentEventNo") Long paymentEventNo,
            @RequestParam("createdDateTime") LocalDateTime createdDateTime,
            @RequestParam("limit") int limit
    );

    @GetMapping(value = "/api/orders/payment-events/{paymentEventNo}/members/{memberNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    PaymentEventResponse selectPaymentOrder(@PathVariable("paymentEventNo") long paymentEventNo, @PathVariable("memberNo") long memberNo);

}