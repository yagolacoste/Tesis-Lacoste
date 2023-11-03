package com.Tesis.bicycle.Service.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.Tesis.bicycle.Dto.Room.RefreshTokenDto;
import com.Tesis.bicycle.Model.Room.Routes;
import com.Tesis.bicycle.Model.Room.Tracked;

import java.util.List;

@Dao
public interface TrackedService {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Tracked Tracked);

    @Query("SELECT * FROM tracked ")
    List<Tracked> getAll();
}
