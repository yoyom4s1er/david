package firm.provider.utils;

import firm.provider.security.jwt.JwtTokenAuthentication;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class JwtUtils {
    public static JwtTokenAuthentication generate(Claims claims) {
        final JwtTokenAuthentication jwtInfoToken = new JwtTokenAuthentication();
        //jwtInfoToken.setFirstName(claims.get("firstName", String.class));
        jwtInfoToken.setMail(claims.getSubject());
        return jwtInfoToken;
    }

    private static Set<GrantedAuthority> getRoles(Claims claims) {
        final List<String> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(role -> new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return role;
                    }
                })
                .collect(Collectors.toSet());
    }
}
