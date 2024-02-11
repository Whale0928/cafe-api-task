package app.practice.cafeapitask.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {

    //COMMON
    INVALID_INPUT_VALUE("잘못된 입력값입니다."),
    INVALID_DATE_VALUE("잘못된 날짜값입니다."),
    INVALID_ENUM_VALUE("허용되지 않은 상수타입입니다.."),

    //Auth
    ALREADY_EXIST_OWNER("이미 존재하는 사용자입니다."),
    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다."),
    NOT_FOUND_OWNER("사용자를 찾을 수 없습니다."),

    //Product
    NOT_FOUND_PRODUCT("상품을 찾을 수 없습니다."),
    NOT_FOUND_PRODUCT_OPTION("상품 옵션을 찾을 수 없습니다."),
    NOT_MATCH_PRODUCT_OPTION("상품 옵션을 찾을 수 없습니다."),
    NOT_ALLOWED_PRODUCT_UPDATE("상품 수정 권한이 없습니다."),
    ;

    private final String message;
}
