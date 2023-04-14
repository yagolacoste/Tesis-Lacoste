package com.Tesis.bicycle.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.Tesis.bicycle.Dto.ApiRest.auth.request.LoginRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.SignupRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.TokenRefreshRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.JwtResponse;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.TokenRefreshResponse;
import com.Tesis.bicycle.Repository.AuthApiRestRepository;

public class AuthViewModel extends AndroidViewModel {

    private final AuthApiRestRepository repository;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        this.repository= AuthApiRestRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<JwtResponse> login(LoginRequest loginRequest){
        return this.repository.login(loginRequest);
    }

    public LiveData<Void> registerUser(SignupRequest dto){
         return this.repository.registerUser(dto);
    }
    public LiveData<TokenRefreshResponse>refreshToken(TokenRefreshRequest refresh){
        return this.repository.refreshToken(refresh);
    }
    public LiveData<Void>logoutUser(TokenRefreshRequest refreshTokenDto){
        return this.repository.logoutUser(refreshTokenDto);
    }
}
