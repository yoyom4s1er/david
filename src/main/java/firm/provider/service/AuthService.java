package firm.provider.service;

import firm.provider.dto.JwtRequest;
import firm.provider.dto.JwtResponse;
import firm.provider.security.jwt.JwtTokenAuthentication;

import javax.security.auth.message.AuthException;

public interface AuthService {

    public JwtResponse login(JwtRequest authRequest) throws AuthException;

    public JwtResponse getAccessToken(String refreshToken) throws AuthException;

    public JwtResponse refresh(String refreshToken) throws AuthException;

    public JwtTokenAuthentication getAuthInfo();
}
