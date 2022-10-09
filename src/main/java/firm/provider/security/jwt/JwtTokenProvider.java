package firm.provider.security.jwt;

import firm.provider.model.Firm;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JwtTokenProvider {

    private SecretKey accessSecret;

    private SecretKey refreshSecret;

    private long validityInMinutes;

    private UserDetailsService userDetailsService;

    @Autowired
    public JwtTokenProvider(
            @Value("${jwt.secret.access}") String AccessSecret,
            @Value("${jwt.secret.refresh}") String RefreshSecret,
            @Value("${jwt.token.expired}")long validityInMinutes,
            UserDetailsService userDetailsService) {
        this.accessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(AccessSecret));
        this.refreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(RefreshSecret));
        this.validityInMinutes = validityInMinutes;
        this.userDetailsService = userDetailsService;
    }
    public String generateAccessToken(Firm firm) {

        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(validityInMinutes).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .setSubject(firm.getName())
                .setExpiration(accessExpiration)
                .signWith(accessSecret)
                .compact();
    }

    public String generateRefreshToken(Firm firm) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusMinutes(validityInMinutes * 6 * 24 * 10).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(firm.getName())
                .setExpiration(refreshExpiration)
                .signWith(refreshSecret)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(accessSecret).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
            return  bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, accessSecret);
    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, refreshSecret);
    }

    private boolean validateToken(String token, Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    /*private List<String> getRoleNames(List<Role> userRoles) {
        List<String> result = new ArrayList<>();

        userRoles.forEach(role -> result.add(role.getName()));

        return result;
    }*/

    public Claims getAccessClaims(String token) {
        return getClaims(token, accessSecret);
    }

    public Claims getRefreshClaims(String token) {
        return getClaims(token, refreshSecret);
    }

    private Claims getClaims(String token, Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
