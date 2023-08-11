package com.Tesis.bicycle.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Tesis.bicycle.Presenter.ApiRestConnection;
import com.Tesis.bicycle.Service.ApiRest.BattleApiService;
import com.Tesis.bicycle.Service.ApiRest.RouteApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class RouteApiRepository {

    private static RouteApiRepository repository;

    private final RouteApiService routeApiService;


    public RouteApiRepository(Context context) {
        this.routeApiService = ApiRestConnection.getServiceRoute(context);
    }

    public static RouteApiRepository getInstance(Context context) {
        if (repository == null) {
            repository = new RouteApiRepository(context);
        }
        return repository;
    }

    public LiveData<Void> addImage( String id,Long photo){
        final MutableLiveData<Void> mld=new MutableLiveData<>();
        routeApiService.addImage(id,photo).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        return mld;
    }
}
