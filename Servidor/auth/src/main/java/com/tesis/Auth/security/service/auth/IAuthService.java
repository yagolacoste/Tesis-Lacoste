package com.tesis.Auth.security.service.auth;

import com.tesis.Auth.Dto.UserDto;
import com.tesis.Auth.payload.request.LoginRequest;
import com.tesis.Auth.payload.request.TokenRefreshRequest;
import com.tesis.Auth.payload.response.JwtResponse;
import com.tesis.Auth.payload.response.TokenRefreshResponse;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<?> authenticate(LoginRequest loginRequest);

    ResponseEntity<?>  register(UserDto userDto);

    ResponseEntity<?> logout(JwtResponse jwtResponse);

    ResponseEntity<?>  refreshToken(TokenRefreshRequest tokenRefreshRequest);
}
