package com.Tesis.bicycle.Service.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.Tesis.bicycle.Model.Room.Route;

import java.util.List;

@Dao
public interface RouteService {

    //add route in room
    @Insert
    void addRoute(Route route);

    //get by id
    @Query("SELECT * FROM Route where id=:id")
    Route getById(long id);

    @Query("Select Count(*) from Route")
    int countRoute();


    @Query("SELECT * FROM Route")
    List<Route> getAll();



}
