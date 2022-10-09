package firm.provider.service.Impl;

import firm.provider.dto.JwtRequest;
import firm.provider.dto.JwtResponse;
import firm.provider.model.Firm;
import firm.provider.security.jwt.JwtTokenAuthentication;
import firm.provider.security.jwt.JwtTokenProvider;
import firm.provider.service.AuthService;
import firm.provider.service.FirmService;
import firm.provider.utils.PasswordEncoder;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final FirmService firmService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtTokenProvider jwtProvider;

    public JwtResponse login(JwtRequest authRequest) throws AuthException {
        final Firm firm = firmService.findByName(authRequest.getUsername())
                .orElseThrow(() -> new AuthException("Пользователь не найден"));

        if (PasswordEncoder.matches(authRequest.getPassword(), firm.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(firm);
            final String refreshToken = jwtProvider.generateRefreshToken(firm);
            refreshStorage.put(firm.getName(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtResponse getAccessToken(String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String name = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(name);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Firm firm = firmService.findByName(name)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(firm);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String name = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(name);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Firm firm = firmService.findByName(name)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(firm);
                final String newRefreshToken = jwtProvider.generateRefreshToken(firm);
                refreshStorage.put(firm.getName(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    public JwtTokenAuthentication getAuthInfo() {
        return (JwtTokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
