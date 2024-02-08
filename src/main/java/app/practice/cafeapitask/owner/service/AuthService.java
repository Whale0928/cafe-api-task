package app.practice.cafeapitask.owner.service;

import app.practice.cafeapitask.global.exception.CustomException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static app.practice.cafeapitask.global.exception.ErrorMessages.ALREADY_EXIST_OWNER;
import static app.practice.cafeapitask.global.exception.ErrorMessages.NOT_FOUND_OWNER;
import static app.practice.cafeapitask.global.exception.ErrorMessages.PASSWORD_NOT_MATCH;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final OwnerRepository ownerRepository;

    public String register(RegisterRequest registerRequest) {

        ownerRepository.findByPhoneNumber(registerRequest.getPhoneNumber())
                .ifPresent(owner -> {
                    throw new CustomException(ALREADY_EXIST_OWNER);
                });

        Owner owner = Owner.builder()
                .phoneNumber(registerRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        ownerRepository.save(owner);
        return jwtTokenProvider.createToken(owner);
    }

    public String login(LoginRequest loginRequest) {

        Owner owner = ownerRepository.findByPhoneNumber(loginRequest.getPhoneNumber())
                .orElseThrow(() -> new CustomException(NOT_FOUND_OWNER));

        if (!passwordEncoder.matches(loginRequest.getPassword(), owner.getPassword()))
            throw new CustomException(PASSWORD_NOT_MATCH);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(owner.getId(), owner.getPhoneNumber())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.createToken(owner);
    }


}
