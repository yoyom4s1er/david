package firm.provider.security.jwt;

import firm.provider.model.Firm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(Firm firm) {
        return new JwtUser(
                firm.getId(),
                firm.getName(),
                firm.getPassword()
        );
    }

    /*public static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }*/
}
