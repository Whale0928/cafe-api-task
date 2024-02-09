package app.practice.cafeapitask.owner.controller;

import app.practice.cafeapitask.global.Object.GlobalResponse;
import app.practice.cafeapitask.owner.dto.request.LoginRequest;
import app.practice.cafeapitask.owner.dto.request.RegisterRequest;
import app.practice.cafeapitask.owner.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 신규 사장님 회원가입 API
     *
     * @param registerRequest the register request
     * @return the response entity
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterRequest registerRequest) {
        String token = authService.register(registerRequest);
        return ResponseEntity.ok(GlobalResponse.success(token));
    }

    /**
     * 사장님 로그인 API
     *
     * @param loginRequest the login request
     * @param response     the response
     * @return the response entity
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
        String token = authService.login(loginRequest);
        response.setHeader("Authorization", "Bearer " + token);
        return ResponseEntity.ok(GlobalResponse.success(token));
    }

    /**
     * 로그아웃 API
     * todo need Impl black list token add logic
     * @param response the response
     * @return the response entity
     */
    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletResponse response) {
        response.setHeader("Authorization", "");
        return ResponseEntity.ok(GlobalResponse.success("Logout successfully"));
    }


}
