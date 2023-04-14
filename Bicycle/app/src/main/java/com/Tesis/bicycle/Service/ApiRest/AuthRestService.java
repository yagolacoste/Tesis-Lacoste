package com.Tesis.bicycle.Service.ApiRest;

import com.Tesis.bicycle.Dto.ApiRest.auth.request.AuthUserDto;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.LoginRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.SignupRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.TokenRefreshRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.JwtResponse;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.MessageResponse;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.TokenRefreshResponse;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.TokenResponse;
import com.Tesis.bicycle.Dto.Room.RefreshTokenDto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthRestService {


    @POST("signin")
    Call<JwtResponse> login(@Body LoginRequest dto);


    @POST("signup")
    Call<Void> registerUser(@Body SignupRequest signupRequest);

    @POST("refreshtoken")
    Call<TokenRefreshResponse> refreshtoken(@Body TokenRefreshRequest refresh);

    @POST("signout")
    Call<Void> logoutUser(@Body TokenRefreshRequest refreshTokenDto);
}
