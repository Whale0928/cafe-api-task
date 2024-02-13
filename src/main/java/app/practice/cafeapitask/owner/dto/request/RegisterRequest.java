package app.practice.cafeapitask.owner.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterRequest {
    @NotNull(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(regexp = "^010[0-9]{8}$", message = "휴대폰 번호는 010으로 시작하는 11자리 숫자여야 합니다.")
    private final String phoneNumber;
    @NotNull(message = "비밀번호를 입력해주세요.")
    private final String password;

    @Builder
    protected RegisterRequest(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
