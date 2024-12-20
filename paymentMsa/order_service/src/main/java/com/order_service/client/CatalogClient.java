package com.order_service.client;

import com.order_service.feign.CatalogFeignConfig;
import com.order_service.domain.SelectProductResponses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "catalog",
        url = "${catalog.url}",
        configuration = {
                CatalogFeignConfig.class,
        }
)
public interface CatalogClient {

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    SelectProductResponses selectProducts(@RequestParam("productNo") List<Long> productNoList);

}