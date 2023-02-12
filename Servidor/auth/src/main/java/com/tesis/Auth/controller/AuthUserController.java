package com.Tesis.auth.controller;

import com.Tesis.auth.dto.TokenDto;
import com.Tesis.auth.payload.request.LoginRequest;
import com.Tesis.auth.payload.request.SignupRequest;
import com.Tesis.auth.payload.request.TokenRefreshRequest;
import com.Tesis.auth.service.IAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthUserController implements IAuthUserController{

    @Autowired
    IAuthUserService authUserService;


    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        return authUserService.authenticateUser(loginRequest);
    }

    @Override
    public ResponseEntity<?> validate(String token) {
        TokenDto tokenDto = authUserService.validate(token);
        if(tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
        return authUserService.registerUser(signUpRequest);
    }

    @Override
    public ResponseEntity<?> refreshtoken(TokenRefreshRequest request) {
        return authUserService.refreshtoken(request);
    }

    @Override
    public ResponseEntity<?> logoutUser() {
        return authUserService.logoutUser();
    }


}
