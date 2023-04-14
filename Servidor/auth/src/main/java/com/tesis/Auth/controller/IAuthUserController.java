package com.Tesis.auth.controller;


import com.Tesis.auth.dto.RefreshTokenDto;
import com.Tesis.auth.entity.RefreshToken;
import com.Tesis.auth.payload.request.LoginRequest;
import com.Tesis.auth.payload.request.SignupRequest;
import com.Tesis.auth.payload.request.TokenRefreshRequest;
import com.Tesis.auth.payload.response.JwtResponse;
import com.Tesis.auth.payload.response.MessageResponse;
import com.Tesis.auth.payload.response.TokenRefreshResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
