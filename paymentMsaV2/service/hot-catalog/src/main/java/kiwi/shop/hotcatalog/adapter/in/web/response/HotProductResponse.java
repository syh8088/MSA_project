package kiwi.shop.hotcatalog.adapter.in.web.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class HotProductResponse {

    private long productNo;
    private String title;
    private LocalDateTime createdDateTime;

    @Builder
    private HotProductResponse(long productNo, String title, LocalDateTime createdDateTime) {
        this.productNo = productNo;
        this.title = title;
        this.createdDateTime = createdDateTime;
    }

    public static HotProductResponse of(long productNo, String title, LocalDateTime createdDateTime) {
        return HotProductResponse.builder()
                .productNo(productNo)
                .title(title)
                .createdDateTime(createdDateTime)
                .build();
    }
}
