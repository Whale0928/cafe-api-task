package app.practice.cafeapitask.global.Object;

import lombok.Builder;

public class GlobalResponse<T> {
    private final int code;
    private final String message;
    private final T data;

    @Builder
    protected GlobalResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> GlobalResponse<T> success(T data) {
        return new GlobalResponse<>(200, "success", data);
    }

    public static <T> GlobalResponse<T> success(String message, T data) {
        return new GlobalResponse<>(200, message, data);
    }

    public static <T> GlobalResponse<T> success(int code, String message, T data) {
        return new GlobalResponse<>(code, message, data);
    }

    public static <T> GlobalResponse<T> fail(String message) {
        return new GlobalResponse<>(400, message, null);
    }

    public static <T> GlobalResponse<T> error(String message) {
        return new GlobalResponse<>(400, message, null);
    }

    public static <T> GlobalResponse<T> error(int code, String message) {
        return new GlobalResponse<>(code, message, null);
    }

}