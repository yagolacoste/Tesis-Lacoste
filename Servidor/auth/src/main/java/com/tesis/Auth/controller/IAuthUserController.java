package com.Tesis.auth.controller;


import com.Tesis.auth.dto.TokenDto;
import com.Tesis.auth.payload.request.LoginRequest;
import com.Tesis.auth.payload.request.SignupRequest;
import com.Tesis.auth.payload.request.TokenRefreshRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@RequestMapping("/auth")
public interface IAuthUserController {


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest);

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestParam String token);//despues revisarlo

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest);

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request);

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser();

    }
