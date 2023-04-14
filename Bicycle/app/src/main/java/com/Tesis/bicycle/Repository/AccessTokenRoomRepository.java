package com.Tesis.bicycle.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.LoginRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.JwtResponse;
import com.Tesis.bicycle.Dto.Room.RefreshTokenDto;
import com.Tesis.bicycle.Model.Room.RefreshToken;
import com.Tesis.bicycle.Presenter.AppDataBase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccessTokenRoomRepository {
    private static AccessTokenRoomRepository repository;
    private final  AppDataBase db;

    public AccessTokenRoomRepository(Context context) {
        this.db = Room.databaseBuilder(context,AppDataBase.class, Constants.NAME_DATA_BASE)
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public static AccessTokenRoomRepository getInstance(Context context){
        if(repository==null){
            repository=new AccessTokenRoomRepository(context);
        }
        return repository;
    }

    public LiveData<List<RefreshTokenDto>>get(){
        final MutableLiveData<List<RefreshTokenDto>> mld=new MutableLiveData<>();
        List<RefreshTokenDto> refreshTokenDto=this.db.refreshTokenService().getAll();
        mld.setValue(refreshTokenDto);
        return mld;
    }


    public void add(RefreshToken refreshToken){
        this.db.refreshTokenService().add(refreshToken);
    }


    public void delete(){
        this.db.refreshTokenService().delete();
    }


    public LiveData<RefreshTokenDto>getFirst(){
        final MutableLiveData<RefreshTokenDto> mld=new MutableLiveData<>();
        RefreshTokenDto refreshTokenDto=this.db.refreshTokenService().getFirst();
        mld.setValue(refreshTokenDto);
        return mld;
    }




}
