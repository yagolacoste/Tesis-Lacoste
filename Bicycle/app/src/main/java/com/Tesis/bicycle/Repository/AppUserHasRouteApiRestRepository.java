package com.Tesis.bicycle.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Tesis.bicycle.Dto.ApiRest.AppUserHasRouteApiRest;
import com.Tesis.bicycle.Presenter.ApiRestConecction;
import com.Tesis.bicycle.Service.ApiRest.AppUserHasRouteRestService;
import com.Tesis.bicycle.Service.ApiRest.AuthRestService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppUserHasRouteApiRestRepository {

    private static AppUserHasRouteApiRestRepository repository;
    private final AppUserHasRouteRestService appUserHasRouteApiRestService;


    public AppUserHasRouteApiRestRepository(Context context) {
        this.appUserHasRouteApiRestService = ApiRestConecction.getServiceAppUserHasRoute(context);
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

}
