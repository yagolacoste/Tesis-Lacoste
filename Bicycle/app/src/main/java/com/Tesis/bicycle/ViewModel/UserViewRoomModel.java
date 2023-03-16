package com.Tesis.bicycle.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.Tesis.bicycle.Dto.ApiRest.auth.request.AuthUserDto;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.LoginRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.TokenRefreshRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.JwtResponse;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.TokenRefreshResponse;
import com.Tesis.bicycle.Repository.UserApiRestRepository;

import retrofit2.Response;

public class UserViewRoomModel extends AndroidViewModel {

    private final UserApiRestRepository repository;

    public UserViewRoomModel(@NonNull Application application) {
        super(application);
        this.repository= UserApiRestRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<JwtResponse> login(LoginRequest loginRequest){
        return this.repository.login(loginRequest);
    }

    public LiveData<Response<?>> validate(String token){
        return this.repository.validate(token);
    }

    public LiveData<Response<?>>registerUser(AuthUserDto dto){
        return this.repository.registerUser(dto);
    }
    public LiveData<TokenRefreshResponse>refreshToken(TokenRefreshRequest refresh){
        return this.repository.refreshToken(refresh);
    }
    public LiveData<Response<?>>logoutUser(){
        return this.repository.logoutUser();
    }
}
