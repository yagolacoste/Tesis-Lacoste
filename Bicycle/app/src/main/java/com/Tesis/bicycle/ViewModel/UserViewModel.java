package com.Tesis.bicycle.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.Tesis.bicycle.Dto.ApiRest.Battle.BattleDto;
import com.Tesis.bicycle.Dto.ApiRest.Battle.ScoreDto;
import com.Tesis.bicycle.Dto.ApiRest.Request.RequestDto;
import com.Tesis.bicycle.Dto.ApiRest.UserAppDto;
import com.Tesis.bicycle.Repository.UserApiRestRepository;

import java.io.IOException;
import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private final UserApiRestRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        this.repository = UserApiRestRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<UserAppDto> getById(Long id){
        return repository.getById(id);
    }

    public LiveData<List<BattleDto>> getBattlesByUser(Long id) {
       return repository.getBattlesByUser(id);
    }

    public LiveData<List<UserAppDto>> getFriends(Long id){
        return repository.getFriends(id);
    }

    public LiveData<Boolean> saveFriend(String email,Long id){
        return repository.addFriend(email,id);
    }

    public LiveData<ScoreDto> getScore(Long id,String email){
        return repository.getScore(id,email);
    }

    public LiveData<Boolean> update(UserAppDto appUserDto,Long id){
        return repository.update(appUserDto,id);
    }

    public LiveData<List<RequestDto>> searchUsers(Long id,Integer status){
        return repository.searchUsers(id,status);
    }
}
