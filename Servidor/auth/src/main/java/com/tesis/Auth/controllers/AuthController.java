package com.tesis.Auth.controllers;

import com.tesis.Auth.Dto.UserDto;
import com.tesis.Auth.payload.request.LoginRequest;
import com.tesis.Auth.payload.request.TokenRefreshRequest;
import com.tesis.Auth.payload.response.JwtResponse;
import com.tesis.Auth.payload.response.TokenRefreshResponse;

import com.tesis.Auth.security.service.auth.IAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements IAuthController{

    private static final Logger LOG= LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private IAuthService authService;

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        return authService.authenticate(loginRequest);
    }

    @Override
    public ResponseEntity<?> register(UserDto userDto) {
        return authService.register(userDto);
    }

    @Override
    public ResponseEntity<?> logout(JwtResponse jwtResponse) {
        return authService.logout(jwtResponse);
    }

    @Override
    public ResponseEntity<?>  refreshToken(TokenRefreshRequest tokenRefreshRequest) {
        return authService.refreshToken(tokenRefreshRequest);
    }
}
