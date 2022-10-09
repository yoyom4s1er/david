package firm.provider.controller;

import firm.provider.dto.JwtRequest;
import firm.provider.dto.JwtResponse;
import firm.provider.dto.RefreshJwtRequest;
import firm.provider.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import java.time.Duration;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Slf4j
public class AuthenticationControllerV1 {

    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) {
        final JwtResponse token;
        try {
            token = authService.login(authRequest);
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }

        ResponseCookie springCookie = ResponseCookie.from("refresh", token.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(30))
                .path("/")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, springCookie.toString()).body(token);
    }

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token;
        try {
            token = authService.getAccessToken(request.getRefreshToken());
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@CookieValue(value = "refresh") String refresh) {
        log.info("New refresh request");
        final JwtResponse token;
        try {
            token = authService.refresh(refresh);
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }

        ResponseCookie springCookie = ResponseCookie.from("refresh", token.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(30))
                .path("/")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, springCookie.toString()).body(token);
    }
}
