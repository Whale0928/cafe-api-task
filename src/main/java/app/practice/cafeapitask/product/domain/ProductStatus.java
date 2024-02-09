package app.practice.cafeapitask.product.domain;

import lombok.Getter;

@Getter
public enum ProductStatus {
    ACTIVE("판매중"),
    INACTIVE("판매중지");

    private final String status;

    ProductStatus(String status) {
        this.status = status;
    }
}
