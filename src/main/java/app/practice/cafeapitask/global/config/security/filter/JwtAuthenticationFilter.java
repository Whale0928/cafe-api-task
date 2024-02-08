package app.practice.cafeapitask.global.config.security.filter;

import app.practice.cafeapitask.global.util.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String authenticationHeader = "Authorization";
    private final String authenticationScheme = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("JWT Filtering....{}", request.getServletPath());

        Optional<String> tokenOptional = parseJwt(request);

        if (tokenOptional.isEmpty()) {
            generateTokenExceptionMessage(response, "인증을 위해 JWT 토큰이 필요합니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = tokenOptional.get();

        log.info(jwtTokenProvider.getRoleByToken(token));

        if (!jwtTokenProvider.isTokenValid(token)) {
            generateTokenExceptionMessage(response, "유효하지 않은 토큰입니다.");
            filterChain.doFilter(request, response);
            return;
        }

        if (StringUtils.hasText(token) && jwtTokenProvider.isTokenValid(token)) {

            Authentication authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {"/api/auth/register", "/api/auth/login"};
        String path = request.getRequestURI();
        return Arrays.asList(excludePath).contains(path);
    }

    private static void generateTokenExceptionMessage(HttpServletResponse response, String message) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.getWriter().println("{ \"message\": \"" + message + "\" }");
    }

    private Optional<String> parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(authenticationHeader);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(authenticationScheme)) {
            return Optional.of(headerAuth.substring(7));
        }
        return Optional.empty();
    }

    private Authentication getAuthentication(String token) {
        String memberEmail = jwtTokenProvider.getPhoneNumberByToken(token);
        log.info("owner: {}", memberEmail);
        return new UsernamePasswordAuthenticationToken(memberEmail, null, new ArrayList<>());
    }
}
