package app.practice.cafeapitask.product.manage.dto;

import lombok.Getter;

@Getter
public enum ProductMessage {
    SUCCESS_UPDATE_PRODUCT("상품 정보를 수정하였습니다."),
    CHANGE_PRODUCT_STATUS("상품 상태를 변경하였습니다."),
    ALREADY_CHANGED_PRODUCT_STATUS("이미 변경된 상품 상태입니다.");

    private final String message;

    ProductMessage(String message) {
        this.message = message;
    }
}
