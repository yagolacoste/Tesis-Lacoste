package com.Tesis.bicycle.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Tesis.bicycle.Dto.ApiRest.Battle.NewBattleDto;
import com.Tesis.bicycle.Dto.ApiRest.Battle.BattleDto;
import com.Tesis.bicycle.Presenter.ApiRestConnection;
import com.Tesis.bicycle.Service.ApiRest.BattleApiService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BattleApiRepository {

    private static BattleApiRepository repository;

    private final BattleApiService battleApiService;


    public BattleApiRepository(Context context) {
        this.battleApiService = ApiRestConnection.getBattleApiService(context);
    }

    public static BattleApiRepository getInstance(Context context) {
        if (repository == null) {
            repository = new BattleApiRepository(context);
        }
        return repository;
    }

    public LiveData<List<BattleDto>> getBattles() throws IOException {
        final MutableLiveData<List<BattleDto>> mld=new MutableLiveData<>();
        battleApiService.getBattles().enqueue(new Callback<List<BattleDto>>() {
            @Override
            public void onResponse(Call<List<BattleDto>> call, Response<List<BattleDto>> response) {
                if(!response.isSuccessful()){
                    mld.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<BattleDto>> call, Throwable t) {

            }
        });

        return mld;
    }

    public LiveData<BattleDto> getById(Long id) {
        final MutableLiveData<BattleDto> mld=new MutableLiveData<>();
        battleApiService.getById(id).enqueue(new Callback<BattleDto>() {
            @Override
            public void onResponse(Call<BattleDto> call, Response<BattleDto> response) {
                if(response.isSuccessful()){
                    mld.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BattleDto> call, Throwable t) {

            }
        });
        return mld;
    }

    public LiveData<Void> addBattle(NewBattleDto battleDto) throws IOException {
        final MutableLiveData<Void> mld=new MutableLiveData<>();
        battleApiService.addBattle(battleDto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Se produjo un error al cargar una batalla"+ t.getMessage());
                t.printStackTrace();
            }
        });

        return mld;
    }


}
