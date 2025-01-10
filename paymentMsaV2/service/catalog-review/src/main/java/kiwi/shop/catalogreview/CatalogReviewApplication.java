package kiwi.shop.catalogreview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "kiwi.shop")
@EnableJpaRepositories(basePackages = "kiwi.shop")
@EnableJpaAuditing
@EnableFeignClients
@SpringBootApplication
public class CatalogReviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogReviewApplication.class, args);
    }
}
