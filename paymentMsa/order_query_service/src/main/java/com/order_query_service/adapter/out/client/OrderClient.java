package com.order_query_service.adapter.out.client;

import com.order_query_service.adapter.in.web.response.PaymentOrderWithSellerResponses;
import com.order_query_service.adapter.out.feign.OrderFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(
        name = "order",
        url = "${order.url}",
        configuration = {
                OrderFeignConfig.class,
        }
)
public interface OrderClient {

    @GetMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    PaymentOrderWithSellerResponses selectPaymentOrderListWithSellerByOrderId(@RequestParam("orderId") String orderId);

}