package com.Tesis.bicycle.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Tesis.bicycle.Dto.ApiRest.auth.request.LoginRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.SignupRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.TokenRefreshRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.JwtResponse;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.TokenRefreshResponse;
import com.Tesis.bicycle.Presenter.ApiRestConecction;
import com.Tesis.bicycle.Service.ApiRest.AuthRestService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthApiRestRepository {
    private static AuthApiRestRepository repository;
    private final AuthRestService authService;


    public AuthApiRestRepository(Context context) {
        this.authService = ApiRestConecction.getAuthService();
    }

    public static AuthApiRestRepository getInstance(Context context){
        if(repository==null){
            repository=new AuthApiRestRepository(context);
        }
        return repository;
    }

    public LiveData<JwtResponse>login(LoginRequest loginRequest){
        final MutableLiveData<JwtResponse>mld=new MutableLiveData<>();
        this.authService.login(loginRequest).enqueue(new Callback<JwtResponse>() {
            @Override
            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {

                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<JwtResponse> call, Throwable t) {
//                mld.setValue(new Response());
                System.out.println("se produjo error al iniciar session"+ t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }


    public LiveData<Void> registerUser(SignupRequest dto){
        final MutableLiveData<Void>mld=new MutableLiveData<>();
        this.authService.registerUser(dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        return mld;
    }

    public LiveData<TokenRefreshResponse>refreshToken(TokenRefreshRequest refresh){
        final MutableLiveData<TokenRefreshResponse>mld=new MutableLiveData<>();
        this.authService.refreshtoken(refresh).enqueue(new Callback<TokenRefreshResponse>() {
            @Override
            public void onResponse(Call<TokenRefreshResponse> call, Response<TokenRefreshResponse> response) {
                if(response.isSuccessful())
                    mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TokenRefreshResponse> call, Throwable t) {

            }
        });

        return mld;
    }

    public LiveData<Void>logoutUser(TokenRefreshRequest refreshTokenDto){
        final MutableLiveData<Void>mld=new MutableLiveData<>();
        this.authService.logoutUser(refreshTokenDto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("se produjo error al salir de session"+ t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
