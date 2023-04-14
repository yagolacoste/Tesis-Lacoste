package com.Tesis.bicycle.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.Tesis.bicycle.Dto.ApiRest.AppUserHasRouteApiRest;
import com.Tesis.bicycle.Dto.ApiRest.AppUserHasRouteDetailsDto;
import com.Tesis.bicycle.Dto.ApiRest.StatisticsDto;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.SignupRequest;
import com.Tesis.bicycle.Repository.AppUserHasRouteApiRestRepository;

import java.util.List;

public class AppUserHasRouteViewModel extends AndroidViewModel {
    private final AppUserHasRouteApiRestRepository repository;


    public AppUserHasRouteViewModel(@NonNull Application application) {
        super(application);
        this.repository=AppUserHasRouteApiRestRepository.getInstance(application.getApplicationContext());

    }

    public LiveData<Void> addStatistic(AppUserHasRouteApiRest appUserHasRouteApiRest){
        return this.repository.addStatistics(appUserHasRouteApiRest);
    }

    public LiveData<AppUserHasRouteDetailsDto> getRouteById(Long id){
        return this.repository.getRouteById(id);
    }

    public LiveData<List<StatisticsDto>> getStatisticByRoute(String routeId){
        return this.repository.getStatisticsByRoute(routeId);
    }
}
