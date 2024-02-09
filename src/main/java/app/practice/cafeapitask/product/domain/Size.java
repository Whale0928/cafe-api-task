package app.practice.cafeapitask.product.domain;

import app.practice.cafeapitask.global.exception.CustomException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static app.practice.cafeapitask.global.exception.ErrorMessages.INVALID_ENUM_VALUE;

@Getter
@AllArgsConstructor
public enum Size {
    SMALL("SMALL"), LARGE("LARGE");

    private final String description;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Size fromString(String value) {
        return Arrays.stream(values())
                .filter(v -> v.getDescription().equals(value))
                .findFirst().orElseThrow(() -> new CustomException(INVALID_ENUM_VALUE));

    }
}
