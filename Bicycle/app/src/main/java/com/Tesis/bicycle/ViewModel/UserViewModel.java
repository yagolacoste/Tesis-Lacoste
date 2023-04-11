package com.Tesis.bicycle.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.Tesis.bicycle.Dto.ApiRest.auth.request.AuthUserDto;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.LoginRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.SignupRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.TokenRefreshRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.JwtResponse;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.MessageResponse;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.TokenRefreshResponse;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.TokenResponse;
import com.Tesis.bicycle.Dto.Room.RefreshTokenDto;
import com.Tesis.bicycle.Repository.UserApiRestRepository;

import retrofit2.Response;

public class UserViewModel extends AndroidViewModel {

    private final UserApiRestRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        this.repository= UserApiRestRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<JwtResponse> login(LoginRequest loginRequest){
        return this.repository.login(loginRequest);
    }

//    public LiveData<TokenResponse> validate(String token){
//        return this.repository.validate(token);
//    }

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
