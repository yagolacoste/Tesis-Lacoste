package com.Tesis.auth.controller;

import com.Tesis.auth.advise.BadRequestException;
import com.Tesis.auth.dto.RefreshTokenDto;
import com.Tesis.auth.payload.request.LoginRequest;
import com.Tesis.auth.payload.request.SignupRequest;
import com.Tesis.auth.payload.request.TokenRefreshRequest;
import com.Tesis.auth.payload.response.JwtResponse;
import com.Tesis.auth.payload.response.MessageResponse;
import com.Tesis.auth.payload.response.TokenRefreshResponse;
import com.Tesis.auth.service.IAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthUserController implements IAuthUserController{

    @Autowired
    IAuthUserService authUserService;


    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        return authUserService.authenticateUser(loginRequest);
    }

    @Override
    public RefreshTokenDto validate(String token) {

        return authUserService.validate(token);
//        if(tokenDto == null)
//            return new BadRequestException()
//        return tokenDto;
    }

    @Override
    public void registerUser(SignupRequest signUpRequest) {
         authUserService.registerUser(signUpRequest);
    }

    @Override
    public TokenRefreshResponse refreshtoken(TokenRefreshRequest request) {
        return authUserService.refreshtoken(request);
    }

    @Override
    public void logoutUser(RefreshTokenDto refreshTokenDto) {
         authUserService.logoutUser(refreshTokenDto);
    }


}
