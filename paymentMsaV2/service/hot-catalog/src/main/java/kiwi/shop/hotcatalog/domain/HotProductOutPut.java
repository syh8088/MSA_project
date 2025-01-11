package kiwi.shop.hotcatalog.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class HotProductOutPut {

    private long productNo;
    private String title;
    private LocalDateTime createdDateTime;

    @Builder
    private HotProductOutPut(long productNo, String title, LocalDateTime createdDateTime) {
        this.productNo = productNo;
        this.title = title;
        this.createdDateTime = createdDateTime;
    }

    public static HotProductOutPut of(long productNo, String title, LocalDateTime createdDateTime) {
        return HotProductOutPut.builder()
                .productNo(productNo)
                .title(title)
                .createdDateTime(createdDateTime)
                .build();
    }
}
