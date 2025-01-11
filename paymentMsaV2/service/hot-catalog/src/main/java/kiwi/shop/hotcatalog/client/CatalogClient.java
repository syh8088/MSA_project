package kiwi.shop.hotcatalog.client;

import kiwi.shop.hotcatalog.domain.SelectProductResponses;
import kiwi.shop.hotcatalog.feign.CatalogFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "catalog",
        url = "${endpoints.catalog.url}",
        configuration = {
                CatalogFeignConfig.class,
        }
)
public interface CatalogClient {

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    SelectProductResponses selectProducts(@RequestParam("productNoList") List<Long> productNoList);

}