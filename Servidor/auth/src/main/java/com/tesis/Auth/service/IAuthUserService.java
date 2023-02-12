package com.Tesis.auth.service;

import com.Tesis.auth.dto.TokenDto;
import com.Tesis.auth.payload.request.LoginRequest;
import com.Tesis.auth.payload.request.SignupRequest;
import com.Tesis.auth.payload.request.TokenRefreshRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthUserService {


    TokenDto validate(String token);

    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);

    ResponseEntity<?> registerUser(SignupRequest signUpRequest);

    ResponseEntity<?> logoutUser();

    ResponseEntity<?> refreshtoken(TokenRefreshRequest request);
}
