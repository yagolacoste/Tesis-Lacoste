package com.Tesis.bicycle.Presenter;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.Tesis.bicycle.Model.Route;
import com.Tesis.bicycle.Service.RouteService;

@Database(entities={Route.class},version = 2)
public abstract class AppDataBase extends RoomDatabase {
    public abstract RouteService routeService();

}
