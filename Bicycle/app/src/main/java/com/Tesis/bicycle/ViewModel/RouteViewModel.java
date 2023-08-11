package com.Tesis.bicycle.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.Tesis.bicycle.Repository.BattleApiRepository;
import com.Tesis.bicycle.Repository.RouteApiRepository;

public class RouteViewModel extends AndroidViewModel {

    private final RouteApiRepository repository;

    public RouteViewModel(@NonNull Application application) {
        super(application);
        repository=RouteApiRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<Void> addImage(String id,Long photo){
        return repository.addImage(id,photo);
    }
}
