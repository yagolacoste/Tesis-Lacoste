package com.Tesis.bicycle.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.Tesis.bicycle.Dto.ApiRest.auth.LoginRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.TokenDto;
import com.Tesis.bicycle.Repository.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private final UserRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        this.repository=UserRepository.getInstance();
    }

    public LiveData<TokenDto> login(LoginRequest loginRequest){
        return this.repository.login(loginRequest);
    }
}
