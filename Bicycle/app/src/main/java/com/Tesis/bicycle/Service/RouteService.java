package com.Tesis.bicycle.Service;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.Tesis.bicycle.Model.Route;

import java.util.List;

@Dao
public interface RouteService {


    @Query("SELECT coordinates FROM Route where id = :id")
    String getCoordinates(int id);

    @Insert
    void insertCoordinates(Route route);

    @Query("SELECT * FROM Route")
    List<Route> getAll();

    @Query("Select Count(*) from Route")
    int countRoute();

}
