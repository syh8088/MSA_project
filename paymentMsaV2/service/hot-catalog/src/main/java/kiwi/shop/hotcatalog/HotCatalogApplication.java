package kiwi.shop.hotcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan(basePackages = "kiwi.shop")
@EnableJpaRepositories(basePackages = "kiwi.shop")
@EnableFeignClients
@EnableScheduling
@SpringBootApplication
public class HotCatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotCatalogApplication.class, args);
    }
}
