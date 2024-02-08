package app.practice.cafeapitask.owner.controller;

import app.practice.cafeapitask.global.Object.GlobalResponse;
import app.practice.cafeapitask.owner.dto.request.LoginRequest;
import app.practice.cafeapitask.owner.dto.request.RegisterRequest;
import app.practice.cafeapitask.owner.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
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

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        String token = authService.register(registerRequest);
        return ResponseEntity.ok(GlobalResponse.success(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        String token = authService.login(loginRequest);
        response.setHeader("Authorization", "Bearer " + token);
        return ResponseEntity.ok(GlobalResponse.success(token));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletResponse response) {
        //todo need Impl black list token add logic
        response.setHeader("Authorization", "");
        return ResponseEntity.ok(GlobalResponse.success("Logout successfully"));
    }


}
