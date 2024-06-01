package firm.provider.security.jwt;

import firm.provider.model.MyUser;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(MyUser user) {
        return new JwtUser(
                user.getId(),
                user.getMail(),
                user.getPassword()
        );
    }

    /*public static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }*/
}
