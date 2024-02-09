package app.practice.cafeapitask.product.manage.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ProductCreateResponse {
    private final Long id;
    private final String message;
    private final LocalDateTime createdAt;

    public static ProductCreateResponse of(Long id) {
        return ProductCreateResponse.builder()
                .id(id)
                .message("Success to create product. id: " + id)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
