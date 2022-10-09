package firm.provider.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    private final static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    public static String encode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public static boolean matches(String password, String encoded) {
        return bCryptPasswordEncoder.matches(password, encoded);
    }
}
