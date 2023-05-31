package com.tesis.Auth.controller;

import com.tesis.Auth.dto.RefreshTokenDto;
import com.tesis.Auth.payload.request.LoginRequest;
import com.tesis.Auth.payload.request.SignupRequest;
import com.tesis.Auth.payload.request.TokenRefreshRequest;
import com.tesis.Auth.payload.response.JwtResponse;
import com.tesis.Auth.payload.response.TokenRefreshResponse;
import com.tesis.Auth.service.IAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
