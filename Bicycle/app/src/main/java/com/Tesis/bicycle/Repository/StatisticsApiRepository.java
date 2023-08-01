package com.Tesis.bicycle.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Dto.ApiRest.Statistics.AchievementsDto;
import com.Tesis.bicycle.Dto.ApiRest.Statistics.ClassificationDto;
import com.Tesis.bicycle.Dto.ApiRest.Statistics.StatisticsApiRest;
import com.Tesis.bicycle.Dto.ApiRest.Statistics.StatisticsDto;
import com.Tesis.bicycle.Presenter.ApiRestConnection;
import com.Tesis.bicycle.Service.ApiRest.StatisticsApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticsApiRepository {

    private static StatisticsApiRepository repository;
    private final StatisticsApiService statisticsApiService;


    public StatisticsApiRepository(Context context) {
        this.statisticsApiService = ApiRestConnection.getServiceStatistics(context);
    }

    public static StatisticsApiRepository getInstance(Context context){
        if(repository==null){
            repository=new StatisticsApiRepository(context);
        }
        return repository;
    }

    public LiveData<Boolean> addStatistics(StatisticsApiRest statisticsApiRest){
        final MutableLiveData<Boolean> mld=new MutableLiveData<>();
        this.statisticsApiService.AddStatistics(statisticsApiRest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful())
                    mld.setValue(true);
                else
                    mld.setValue(false);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        return mld;
    }

    public LiveData<List<RouteDetailsDto>> getRouteById(Long id){
        final MutableLiveData<List<RouteDetailsDto>> mld=new MutableLiveData<>();
        this.statisticsApiService.getRouteById(id).enqueue(new Callback<List<RouteDetailsDto>>() {
            @Override
            public void onResponse(Call<List<RouteDetailsDto>> call, Response<List<RouteDetailsDto>> response) {
                if (response.isSuccessful()) {
                    mld.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<RouteDetailsDto>> call, Throwable t) {

            }
        });
        return mld;
    }

    public LiveData<List<StatisticsDto>> getStatisticsByRoute(String routeId){
        final MutableLiveData<List<StatisticsDto>> mld=new MutableLiveData<>();
        statisticsApiService.getStatisticsByRoute(routeId).enqueue(new Callback<List<StatisticsDto>>() {
            @Override
            public void onResponse(Call<List<StatisticsDto>> call, Response<List<StatisticsDto>> response) {
                if(response.isSuccessful()){
                    mld.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<StatisticsDto>> call, Throwable t) {

            }
        });
        return mld;
    }

    public LiveData<AchievementsDto> getAchievementsByUser(Long appUser){
        final MutableLiveData<AchievementsDto> mld=new MutableLiveData<>();
        statisticsApiService.getAchievementsByUser(appUser).enqueue(new Callback<AchievementsDto>() {
            @Override
            public void onResponse(Call<AchievementsDto> call, Response<AchievementsDto> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<AchievementsDto> call, Throwable t) {

            }
        });
        return mld;
    }

    public LiveData<List<ClassificationDto>> getAchievements(){
        final MutableLiveData<List<ClassificationDto>> mld=new MutableLiveData<>();
        statisticsApiService.getAchievements().enqueue(new Callback<List<ClassificationDto>>() {
            @Override
            public void onResponse(Call<List<ClassificationDto>> call, Response<List<ClassificationDto>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ClassificationDto>> call, Throwable t) {

            }
        });
        return mld;
    }
}
