package com.Tesis.bicycle.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Tesis.bicycle.Dto.Room.RefreshTokenDto;
import com.Tesis.bicycle.Dto.Room.TrackedDto;
import com.Tesis.bicycle.Model.Room.Tracked;
import com.Tesis.bicycle.Repository.AccessTokenRoomRepository;
import com.Tesis.bicycle.Repository.TrackedRepository;

import java.util.List;

public class TrackedRoomViewModel extends AndroidViewModel {

    private final TrackedRepository repository;

    public TrackedRoomViewModel(@NonNull Application application) {
        super(application);
        this.repository = TrackedRepository.getInstance(application.getApplicationContext());
    }


    public void add(Tracked tracked){
        this.repository.add(tracked);
    }

    public LiveData<List<TrackedDto>> getAll(){
       return this.repository.getAll();
    }

    public void deleteById(String id){
        this.repository.deleteById(id);
    }

}
