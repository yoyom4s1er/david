package firm.provider.service.Impl;

import firm.provider.dto.JwtRequest;
import firm.provider.dto.JwtResponse;
import firm.provider.model.MyUser;
import firm.provider.security.jwt.JwtTokenAuthentication;
import firm.provider.security.jwt.JwtTokenProvider;
import firm.provider.service.AuthService;
import firm.provider.service.MyUserService;
import firm.provider.utils.PasswordEncoder;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MyUserService userService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtTokenProvider jwtProvider;

    public JwtResponse login(JwtRequest authRequest) throws AuthException {
        final MyUser user = userService.getUser(authRequest.getMail())
                .orElseThrow(() -> new AuthException("Пользователь не найден"));

        if (PasswordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getMail(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtResponse getAccessToken(String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String mail = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(mail);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final MyUser user = userService.getUser(mail)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String mail = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(mail);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final MyUser user = userService.getUser(mail)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getMail(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    public JwtTokenAuthentication getAuthInfo() {
        return (JwtTokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
