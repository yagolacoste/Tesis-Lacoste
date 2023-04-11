package com.Tesis.bicycle.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.Tesis.bicycle.Dto.ApiRest.AppUserHasRouteApiRest;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.SignupRequest;
import com.Tesis.bicycle.Repository.AppUserHasRouteApiRestRepository;

public class AppUserHasRouteViewModel extends AndroidViewModel {
    private final AppUserHasRouteApiRestRepository repository;


    public AppUserHasRouteViewModel(@NonNull Application application) {
        super(application);
        this.repository=AppUserHasRouteApiRestRepository.getInstance(application.getApplicationContext());

    }

    public LiveData<Void> addStatistic(AppUserHasRouteApiRest appUserHasRouteApiRest){
        return this.repository.addStatistics(appUserHasRouteApiRest);
    }
}
