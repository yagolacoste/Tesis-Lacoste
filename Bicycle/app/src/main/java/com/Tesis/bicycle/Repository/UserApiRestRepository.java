package com.Tesis.bicycle.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Tesis.bicycle.Dto.ApiRest.Battle.BattleDto;
import com.Tesis.bicycle.Dto.ApiRest.Battle.ScoreDto;
import com.Tesis.bicycle.Dto.ApiRest.UserAppDto;
import com.Tesis.bicycle.Presenter.ApiRestConnection;
import com.Tesis.bicycle.Service.ApiRest.UserApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserApiRestRepository {

    private static UserApiRestRepository repository;
    private final UserApiService userApiService;


    public UserApiRestRepository(Context context) {
        this.userApiService = ApiRestConnection.getUserService(context);
    }

    public static UserApiRestRepository getInstance(Context context) {
        if (repository == null) {
            repository = new UserApiRestRepository(context);
        }
        return repository;
    }

    public LiveData<UserAppDto> getById(Long id) {
        final MutableLiveData<UserAppDto> mld = new MutableLiveData<>();
        userApiService.getById(id).enqueue(new Callback<UserAppDto>() {
            @Override
            public void onResponse(Call<UserAppDto> call, Response<UserAppDto> response) {
                if (response.isSuccessful()) {
                    mld.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserAppDto> call, Throwable t) {

            }
        });
        return mld;
    }

    public LiveData<List<BattleDto>> getBattlesByUser(Long id) {
        final MutableLiveData<List<BattleDto>> mld = new MutableLiveData<>();
        userApiService.getBattleByUser(id).enqueue(new Callback<List<BattleDto>>() {
            @Override
            public void onResponse(Call<List<BattleDto>> call, Response<List<BattleDto>> response) {
                if (response.isSuccessful()) {
                    mld.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<BattleDto>> call, Throwable t) {
                System.out.println("se produjo al traer las batallas" + t.getMessage());
                t.printStackTrace();

            }
        });
        return mld;
    }

    public LiveData<List<UserAppDto>> getFriends(Long id) {
        final MutableLiveData<List<UserAppDto>> mld = new MutableLiveData<>();
        userApiService.getFriends(id).enqueue(new Callback<List<UserAppDto>>() {
            @Override
            public void onResponse(Call<List<UserAppDto>> call, Response<List<UserAppDto>> response) {
                if (response.isSuccessful()) {
                    mld.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<UserAppDto>> call, Throwable t) {
                System.out.println("se produjo error al traer las usuarios" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<Boolean> addFriend(String email, Long id) {
        final MutableLiveData<Boolean> mld = new MutableLiveData<>();
        userApiService.saveFriend(id, email).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    mld.setValue(true);
                } else mld.setValue(false);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        return mld;
    }


    public LiveData<ScoreDto> getScore(Long id, String email) {
        final MutableLiveData<ScoreDto> mld = new MutableLiveData<>();
        userApiService.getScore(id, email).enqueue(new Callback<ScoreDto>() {
            @Override
            public void onResponse(Call<ScoreDto> call, Response<ScoreDto> response) {
                if (response.isSuccessful()) {
                    mld.setValue(response.body());
                } else mld.setValue(null);
            }

            @Override
            public void onFailure(Call<ScoreDto> call, Throwable t) {

            }
        });
        return mld;
    }

    public LiveData<Boolean> update(UserAppDto appUserDto, Long id) {
        final MutableLiveData<Boolean> mld = new MutableLiveData<>();
        userApiService.update(id,appUserDto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    mld.setValue(true);
                }
                else mld.setValue(false);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        return mld;
    }
}
