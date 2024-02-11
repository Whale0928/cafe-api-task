package app.practice.cafeapitask.product.manage.dto.request;


import app.practice.cafeapitask.product.domain.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ProductUpdateRequest {

    @NotBlank(message = "카테고리:기존 값 혹은 새로운 값은 필수입니다.")
    private final String category;

    @NotNull(message = "가격은: 기존 값 혹은 새로운 값은 필수입니다.")
    @Positive(message = "가격은 0보다 커야 합니다.")
    private final Double price;

    @NotNull(message = "원가: 기존 값 혹은 새로운 값은 필수입니다.")
    @Positive(message = "원가는 0보다 커야 합니다.")
    private final Double cost;

    @NotBlank(message = "상품명: 기존 값 혹은 새로운 값은 필수입니다.")
    private final String name;

    @NotBlank(message = "상품 설명: 기존 값 혹은 새로운 값은 필수입니다.")
    private final String description;

    @NotBlank(message = "바코드: 기존 값 혹은 새로운 값은 필수입니다.")
    private final String barcode;

    @NotNull(message = "유통기한: 기존 값 혹은 새로운 값은 필수입니다.")
    private final LocalDate expirationDate;

    @NotNull(message = "사이즈: 기존 값 혹은 새로운 값은 필수입니다.")
    private final Size size;

    @Builder
    public ProductUpdateRequest(String category, Double price, Double cost, String name, String description, String barcode, LocalDate expirationDate, Size size) {
        this.category = category;
        this.price = price;
        this.cost = cost;
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.expirationDate = expirationDate;
        this.size = size;
    }
}