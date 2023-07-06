package com.Tesis.bicycle.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Dto.ApiRest.StatisticsApiRest;
import com.Tesis.bicycle.Dto.ApiRest.StatisticsDto;
import com.Tesis.bicycle.Repository.StatisticsApiRepository;

import java.util.List;

public class StatisticsViewModel extends AndroidViewModel {
    private final StatisticsApiRepository repository;


    public StatisticsViewModel(@NonNull Application application) {
        super(application);
        this.repository= StatisticsApiRepository.getInstance(application.getApplicationContext());

    }

    public LiveData<Boolean> addStatistic(StatisticsApiRest statisticsApiRest){
        return this.repository.addStatistics(statisticsApiRest);
    }

    public LiveData<List<RouteDetailsDto>> getRouteById(Long id){
        return this.repository.getRouteById(id);
    }

    public LiveData<List<StatisticsDto>> getStatisticByRoute(String routeId){
        return this.repository.getStatisticsByRoute(routeId);
    }
}
