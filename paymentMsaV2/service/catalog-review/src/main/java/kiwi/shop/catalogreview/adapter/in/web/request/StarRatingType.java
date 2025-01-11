package kiwi.shop.catalogreview.adapter.in.web.request;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum StarRatingType {

    ONE("ONE", 1L),
    TWO("TWO", 2L),
    THREE("THREE", 3L),
    FOUR("FOUR", 4L),
    FIVE("FIVE", 5L)
    ;

    private final String type;
    private final long starRating;

    StarRatingType(String type, long starRating) {
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
