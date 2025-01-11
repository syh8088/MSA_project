package kiwi.shop.catalogreview.adapter.in.web.request;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;

@Getter
public enum StarRatingType {

    ONE("ONE", BigDecimal.ONE),
    TWO("TWO", BigDecimal.valueOf(2)),
    THREE("THREE", BigDecimal.valueOf(3)),
    FOUR("FOUR", BigDecimal.valueOf(4)),
    FIVE("FIVE", BigDecimal.valueOf(5))
    ;

    private final String type;
    private final BigDecimal starRating;

    StarRatingType(String type, BigDecimal starRating) {
        this.type = type;
        this.starRating = starRating;
    }

    public String getStarRatingType() {
        return this.type;
    }

    public static StarRatingType getByStarRatingType(String type) {
        return Arrays.stream(StarRatingType.values())
                .filter(data -> data.getStarRatingType().equals(type))
                .findFirst()
                .orElse(null);
    }
}
