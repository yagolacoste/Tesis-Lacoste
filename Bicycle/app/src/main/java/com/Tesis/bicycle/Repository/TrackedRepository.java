package com.Tesis.bicycle.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.Room.RefreshTokenDto;
import com.Tesis.bicycle.Dto.Room.TrackedDto;
import com.Tesis.bicycle.Model.Room.RefreshToken;
import com.Tesis.bicycle.Model.Room.Tracked;
import com.Tesis.bicycle.Presenter.AppDataBase;

import java.util.List;

public class TrackedRepository {

    private static TrackedRepository repository;
    private final AppDataBase db;

    public TrackedRepository(Context context) {
        this.db = Room.databaseBuilder(context,AppDataBase.class, Constants.NAME_DATA_BASE)
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public static TrackedRepository getInstance(Context context){
        if(repository==null){
            repository=new TrackedRepository(context);
        }
        return repository;
    }

    public void add(Tracked tracked){
        this.db.trackedService().add(tracked);
    }

    public LiveData<List<TrackedDto>> getAll(){
        final MutableLiveData<List<TrackedDto>> mld=new MutableLiveData<>();
        mld.setValue(this.db.trackedService().getAll());
        return mld;
    }

    public void deleteById(String id){
        this.db.trackedService().deleteById(id);
    }


}
