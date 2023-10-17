package com.Tesis.bicycle.Service.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.Tesis.bicycle.Model.Room.Routes;


@Dao
public interface RoutesService {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Routes route);
}
