package com.Tesis.bicycle.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Tesis.bicycle.Dto.ApiRest.auth.request.AuthUserDto;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.LoginRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.TokenRefreshRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.JwtResponse;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.TokenRefreshResponse;
import com.Tesis.bicycle.Presenter.ApiRestConecction;
import com.Tesis.bicycle.Service.ApiRest.AuthRestService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserApiRestRepository {
    private static UserApiRestRepository repository;
    private final AuthRestService authService;


    public UserApiRestRepository(Context context) {
        this.authService = ApiRestConecction.getAuthService(context);
    }

    public static UserApiRestRepository getInstance(Context context){
        if(repository==null){
            repository=new UserApiRestRepository(context);
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


    public LiveData<Response<?>>validate(String token){
        final MutableLiveData<Response<?>>mld=new MutableLiveData<>();
        this.authService.validate(token).enqueue(new Callback<Response<?>>() {
            @Override
            public void onResponse(Call<Response<?>> call, Response<Response<?>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Response<?>> call, Throwable t) {
                System.out.println("se produjo error al validar token"+ t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<Response<?>>registerUser(AuthUserDto dto){
        final MutableLiveData<Response<?>>mld=new MutableLiveData<>();
        this.authService.registerUser(dto).enqueue(new Callback<Response<?>>() {
            @Override
            public void onResponse(Call<Response<?>> call, Response<Response<?>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Response<?>> call, Throwable t) {
                System.out.println("se produjo error al iniciar session"+ t.getMessage());
                t.printStackTrace();
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

    public LiveData<Response<?>>logoutUser(){
        final MutableLiveData<Response<?>>mld=new MutableLiveData<>();
        this.authService.logoutUser().enqueue(new Callback<Response<?>>() {
            @Override
            public void onResponse(Call<Response<?>> call, Response<Response<?>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Response<?>> call, Throwable t) {
                System.out.println("se produjo error al salir de session"+ t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
