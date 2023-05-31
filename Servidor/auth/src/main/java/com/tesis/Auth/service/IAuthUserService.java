package com.tesis.Auth.service;

import com.tesis.Auth.dto.RefreshTokenDto;
import com.tesis.Auth.payload.request.LoginRequest;
import com.tesis.Auth.payload.request.SignupRequest;
import com.tesis.Auth.payload.request.TokenRefreshRequest;
import com.tesis.Auth.payload.response.JwtResponse;
import com.tesis.Auth.payload.response.TokenRefreshResponse;


public interface IAuthUserService {

    JwtResponse authenticateUser(LoginRequest loginRequest);

    RefreshTokenDto validate(String token);

    void registerUser(SignupRequest signUpRequest);

    void logoutUser(RefreshTokenDto refreshTokenDto);

    TokenRefreshResponse refreshtoken(TokenRefreshRequest request);
}
