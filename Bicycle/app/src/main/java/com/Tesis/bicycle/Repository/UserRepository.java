package com.Tesis.bicycle.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Tesis.bicycle.Dto.ApiRest.auth.LoginRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.TokenDto;
import com.Tesis.bicycle.Presenter.ApiRestConecction;
import com.Tesis.bicycle.Service.ApiRest.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static UserRepository repository;
    private final AuthService authService;


    public UserRepository() {
        this.authService = ApiRestConecction.getAuthService();
    }

    public static UserRepository getInstance(){
        if(repository==null){
            repository=new UserRepository();
        }
        return repository;
    }
    public LiveData<TokenDto>login(LoginRequest loginRequest){
        final MutableLiveData<TokenDto>mld=new MutableLiveData<>();
        this.authService.login(loginRequest).enqueue(new Callback<TokenDto>() {
            @Override
            public void onResponse(Call<TokenDto> call, Response<TokenDto> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TokenDto> call, Throwable t) {
                mld.setValue(new TokenDto());
                System.out.println("se produjo error al iniciar session"+ t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
