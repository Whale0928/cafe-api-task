package app.practice.cafeapitask.owner.controller;


import app.practice.cafeapitask.global.exception.GlobalExceptionHandler;
import app.practice.cafeapitask.owner.dto.request.LoginRequest;
import app.practice.cafeapitask.owner.dto.request.RegisterRequest;
import app.practice.cafeapitask.owner.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class AuthControllerTest {

    protected ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService;
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(authService))
                .setControllerAdvice(new GlobalExceptionHandler()) // GlobalExceptionHandler 인스턴스 직접 생성
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가
                .alwaysDo(print())
                .build();

        registerRequest = RegisterRequest.builder()
                .phoneNumber("testUser")
                .password("password")
                .build();
        loginRequest = LoginRequest.builder()
                .phoneNumber("testUser")
                .password("password")
                .build();
    }

    @Test
    @DisplayName("사용자 등록 시 JWT 토큰 반환")
    void whenRegisterUser_thenReturnsJwtToken() throws Exception {

        String token = "fakeToken";

        when(authService.register(registerRequest)).thenReturn(token);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));
    }

    @Test
    @DisplayName("사용자 로그인 시 JWT 토큰 반환")
    void whenAuthenticateUser_thenReturnsJwtToken() throws Exception {
        String token = "fakeToken";

        when(authService.login(loginRequest)).thenReturn(token);
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));
    }

    @Test
    @DisplayName("사용자가 로그아웃시 토큰이 삭제된다.")
    void whenLogoutUser_thenTokenIsDeleted() throws Exception {
        mockMvc.perform(get("/api/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + "fakeToken"))
                .andExpect(status().isOk())
                .andExpect(header().string("Authorization", ""));
    }
}