package com.Tesis.bicycle.Service.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.Tesis.bicycle.Dto.Room.TrackedDto;
import com.Tesis.bicycle.Model.Room.Tracked;

import java.util.List;

@Dao
public interface TrackedService {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Tracked Tracked);

    @Query("SELECT * FROM tracked")
    List<TrackedDto> getAll();


    @Query("DELETE FROM tracked where routeId =:routeId ")
    void  deleteById(String routeId);
}
