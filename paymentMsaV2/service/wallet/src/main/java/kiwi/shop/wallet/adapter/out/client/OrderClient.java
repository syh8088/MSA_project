package kiwi.shop.wallet.adapter.out.client;

import kiwi.shop.wallet.adapter.out.feign.OrderFeignConfig;
import kiwi.shop.wallet.domain.PaymentOrderWithSellerResponses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(
        name = "order",
        url = "${endpoints.order.url}",
        configuration = {
                OrderFeignConfig.class,
        }
)
public interface OrderClient {

    @GetMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    PaymentOrderWithSellerResponses selectPaymentOrderListWithSellerByOrderId(@RequestParam("orderId") String orderId);

}