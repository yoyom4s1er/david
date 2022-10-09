package firm.provider.security;

import firm.provider.model.Firm;
import firm.provider.security.jwt.JwtUserFactory;
import firm.provider.service.FirmService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final FirmService firmService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Firm firm = firmService.findByName(username).orElseThrow(
                () -> new UsernameNotFoundException("User with username: " + username + " not found")
        );

        return JwtUserFactory.create(firm);
    }
}
