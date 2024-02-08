package app.practice.cafeapitask.global.exception;


import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final int code;
    private final String message;

    public CustomException(ErrorMessages message) {
        this.code = 400;
        this.message = message.getMessage();
    }

    public CustomException(int code, ErrorMessages message) {
        this.code = code;
        this.message = message.getMessage();
    }
}
