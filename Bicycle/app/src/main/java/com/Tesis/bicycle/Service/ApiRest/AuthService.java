package com.Tesis.bicycle.Service.ApiRest;

import com.Tesis.bicycle.Dto.ApiRest.auth.AuthUserDto;
import com.Tesis.bicycle.Dto.ApiRest.auth.LoginRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.TokenDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface AuthService {

    @POST("/login")
    Call<TokenDto> login(@Body LoginRequest dto);

    @POST("/validate")
     Call<TokenDto> validate(@Field("token") String token);

    @POST("/create")
     Call<AuthUserDto> create(@Body AuthUserDto dto);
}
