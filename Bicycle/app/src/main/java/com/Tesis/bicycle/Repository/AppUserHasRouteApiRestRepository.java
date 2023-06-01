package com.Tesis.bicycle.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Tesis.bicycle.Dto.ApiRest.AppUserHasRouteApiRest;
import com.Tesis.bicycle.Dto.ApiRest.AppUserHasRouteDetailsDto;
import com.Tesis.bicycle.Dto.ApiRest.StatisticsDto;
import com.Tesis.bicycle.Presenter.ApiRestConnection;
import com.Tesis.bicycle.Service.ApiRest.AppUserHasRouteApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppUserHasRouteApiRestRepository {

    private static AppUserHasRouteApiRestRepository repository;
    private final AppUserHasRouteApiService appUserHasRouteApiRestService;


    public AppUserHasRouteApiRestRepository(Context context) {
        this.appUserHasRouteApiRestService = ApiRestConnection.getServiceAppUserHasRoute(context);
    }

    public static AppUserHasRouteApiRestRepository getInstance(Context context){
        if(repository==null){
            repository=new AppUserHasRouteApiRestRepository(context);
        }
        return repository;
    }

    public LiveData<Void> addStatistics(AppUserHasRouteApiRest appUserHasRouteApiRest){
        final MutableLiveData<Void> mld=new MutableLiveData<>();
        this.appUserHasRouteApiRestService.AddStatistics(appUserHasRouteApiRest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        return mld;
    }

    public LiveData<AppUserHasRouteDetailsDto> getRouteById(Long id){
        final MutableLiveData<AppUserHasRouteDetailsDto> mld=new MutableLiveData<>();
        this.appUserHasRouteApiRestService.getRouteById(id).enqueue(new Callback<AppUserHasRouteDetailsDto>() {
            @Override
            public void onResponse(Call<AppUserHasRouteDetailsDto> call, Response<AppUserHasRouteDetailsDto> response) {
                if (response.isSuccessful()) {
                    mld.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<AppUserHasRouteDetailsDto> call, Throwable t) {

            }
        });
        return mld;
    }

    public LiveData<List<StatisticsDto>> getStatisticsByRoute(String routeId){
        final MutableLiveData<List<StatisticsDto>> mld=new MutableLiveData<>();
        appUserHasRouteApiRestService.getStatisticsByRoute(routeId).enqueue(new Callback<List<StatisticsDto>>() {
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
}
