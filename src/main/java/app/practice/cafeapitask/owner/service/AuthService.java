package app.practice.cafeapitask.owner.service;

import app.practice.cafeapitask.global.util.jwt.JwtTokenProvider;
import app.practice.cafeapitask.owner.domain.Owner;
import app.practice.cafeapitask.owner.dto.request.LoginRequest;
import app.practice.cafeapitask.owner.dto.request.RegisterRequest;
import app.practice.cafeapitask.owner.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final OwnerRepository ownerRepository;

    public String register(RegisterRequest registerRequest) {
        Owner owner = Owner.builder()
                .phoneNumber(registerRequest.getPhoneNumber())
                .password(registerRequest.getPassword())
                .build();
        ownerRepository.save(owner);
        return jwtTokenProvider.createToken(owner);
    }

    public String login(LoginRequest loginRequest) {

        Owner owner = ownerRepository.findByPhoneNumber(loginRequest.getPhoneNumber()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(owner.getId(), owner.getPhoneNumber())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.createToken(owner);
    }


}
