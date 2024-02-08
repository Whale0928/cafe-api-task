package app.practice.cafeapitask.owner.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterRequest {
    private final String phoneNumber;
    private final String password;

    @Builder
    protected RegisterRequest(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
