package app.practice.cafeapitask.owner.service;

import app.practice.cafeapitask.global.exception.CustomException;
import app.practice.cafeapitask.global.util.jwt.JwtTokenProvider;
import app.practice.cafeapitask.owner.domain.Owner;
import app.practice.cafeapitask.owner.dto.request.LoginRequest;
import app.practice.cafeapitask.owner.dto.request.RegisterRequest;
import app.practice.cafeapitask.owner.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    private final String phoneNumber = "1234567890";
    private final String password = "password";
    private final String encodedPassword = "encodedPassword";
    private final String jwtToken = "jwtToken";
    @InjectMocks
    private AuthService authService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private OwnerRepository ownerRepository;
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private Owner owner;

    @BeforeEach
    void setUp() {
        registerRequest = RegisterRequest.builder()
                .phoneNumber(phoneNumber)
                .password(password)
                .build();

        loginRequest = LoginRequest.builder()
                .phoneNumber(phoneNumber)
                .password(password)
                .build();

        owner = Owner.builder()
                .phoneNumber(phoneNumber)
                .password(encodedPassword)
                .build();
        lenient().when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        lenient().when(jwtTokenProvider.createToken(any(Owner.class))).thenReturn(jwtToken);
    }


    @Test
    @DisplayName("성공적으로 등록하면 JWT 토큰을 반환한다")
    void register_Success() {
        // Given: 등록 요청이 주어졌을 때
        when(ownerRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.empty());
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        // When: 사용자가 등록을 시도하면
        String resultToken = authService.register(registerRequest);

        // Then: JWT 토큰이 반환되어야 한다
        assertEquals(jwtToken, resultToken);
        verify(ownerRepository).save(any(Owner.class));
    }

    @Test
    @DisplayName("전화번호가 이미 존재하면 CustomException을 발생시킨다")
    void register_ThrowsException_WhenPhoneNumberExists() {
        // Given: 전화번호가 이미 존재할 때
        when(ownerRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(owner));
        // When & Then: 등록을 시도하면 CustomException이 발생해야 한다
        assertThrows(CustomException.class, () -> authService.register(registerRequest));
    }


    @Test
    @DisplayName("로그인 성공 시 JWT 토큰을 반환한다")
    void login_Success() {
        // Given: 올바른 전화번호와 비밀번호가 주어졌을 때
        when(ownerRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(owner));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // When: 사용자가 로그인을 시도하면
        String resultToken = authService.login(loginRequest);

        // Then: JWT 토큰이 반환되어야 한다
        assertEquals(jwtToken, resultToken);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    @DisplayName("사용자를 찾을 수 없으면 CustomException이 발생한다")
    void login_ThrowsException_WhenUserNotFound() {
        // Given : 제공된 전화번호로 사용자를 찾을 수 없을 때
        when(ownerRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.empty());

        // when : 로그인을 시도하면
        // then : CustomException이 발생해야 한다
        assertThrows(CustomException.class, () -> authService.login(loginRequest));
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 CustomException이 발생한다")
    void login_ThrowsException_WhenPasswordNotMatch() {
        // given: 사용자는 찾았지만 제공된 비밀번호가 저장된 비밀번호와 일치하지 않을 때
        when(ownerRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(owner));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        // when : 로그인을 시도하면
        // then: CustomException이 발생해야 한다
        assertThrows(CustomException.class, () -> authService.login(loginRequest));
    }
}