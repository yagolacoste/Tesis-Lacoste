package com.Tesis.auth.service;

import com.Tesis.auth.dto.RefreshTokenDto;
import com.Tesis.auth.payload.request.LoginRequest;
import com.Tesis.auth.payload.request.SignupRequest;
import com.Tesis.auth.payload.request.TokenRefreshRequest;
import com.Tesis.auth.payload.response.JwtResponse;
import com.Tesis.auth.payload.response.MessageResponse;
import com.Tesis.auth.payload.response.TokenRefreshResponse;
import org.springframework.http.ResponseEntity;

public interface IAuthUserService {

    JwtResponse authenticateUser(LoginRequest loginRequest);

    RefreshTokenDto validate(String token);

    MessageResponse registerUser(SignupRequest signUpRequest);

    void logoutUser(RefreshTokenDto refreshTokenDto);

    TokenRefreshResponse refreshtoken(TokenRefreshRequest request);
}
