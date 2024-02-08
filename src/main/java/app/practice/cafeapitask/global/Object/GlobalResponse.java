package app.practice.cafeapitask.global.Object;

import lombok.Builder;

public class GlobalResponse<T> {
    private final boolean success;
    private final String message;
    private final T data;

    @Builder
    protected GlobalResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> GlobalResponse<T> success(T data) {
        return new GlobalResponse<>(true, "success", data);
    }

    public static <T> GlobalResponse<T> success(T data, String message) {
        return new GlobalResponse<>(true, message, data);
    }

    public static <T> GlobalResponse<T> failure(String message) {
        return new GlobalResponse<>(false, message, null);
    }

}