package com.Tesis.bicycle.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.Tesis.bicycle.Dto.Room.RefreshTokenDto;
import com.Tesis.bicycle.Model.Room.RefreshToken;
import com.Tesis.bicycle.Repository.AccessTokenRoomRepository;

import java.util.List;

public class AccessTokenRoomViewModel extends AndroidViewModel {

    private final AccessTokenRoomRepository repository;


    public AccessTokenRoomViewModel(@NonNull Application application) {
        super(application);
        this.repository=AccessTokenRoomRepository.getInstance(application.getApplicationContext());

    }

    public LiveData<List<RefreshToken>> getAccessToken(){
        return repository.get();
    }

    public void addAccessToken(RefreshToken refreshToken){
        this.repository.add(refreshToken);
    }

}
