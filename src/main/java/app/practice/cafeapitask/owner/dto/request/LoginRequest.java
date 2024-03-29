package app.practice.cafeapitask.owner.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotNull(message = "휴대폰 번호를 입력해주세요.")
    private final String phoneNumber;
    @NotNull(message = "비밀번호를 입력해주세요.")
    private final String password;

    @Builder
    protected LoginRequest(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
