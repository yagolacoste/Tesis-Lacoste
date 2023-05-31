package com.tesis.Auth.controller;


import com.tesis.Auth.dto.RefreshTokenDto;
import com.tesis.Auth.payload.request.LoginRequest;
import com.tesis.Auth.payload.request.SignupRequest;
import com.tesis.Auth.payload.request.TokenRefreshRequest;
import com.tesis.Auth.payload.response.JwtResponse;
import com.tesis.Auth.payload.response.TokenRefreshResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/auth")
public interface IAuthUserController {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/signin",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest);

    @PostMapping(value = "/validate",produces = MediaType.APPLICATION_JSON_VALUE)
    public RefreshTokenDto validate(@RequestParam String token);

    @PostMapping(value = "/signup",produces = MediaType.APPLICATION_JSON_VALUE)
    public void registerUser(@Valid @RequestBody SignupRequest signUpRequest);

    @PostMapping("/refreshtoken")
    public TokenRefreshResponse refreshtoken(@Valid @RequestBody TokenRefreshRequest request);

    @PostMapping(value = "/signout",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void logoutUser(@RequestBody RefreshTokenDto refreshTokenDto);

    }
