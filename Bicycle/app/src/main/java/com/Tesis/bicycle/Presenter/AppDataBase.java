package com.Tesis.bicycle.Presenter;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.Tesis.bicycle.Converters.ConvertersDate;
import com.Tesis.bicycle.Model.Room.AppUserHasRoute;
import com.Tesis.bicycle.Model.Room.Route;
import com.Tesis.bicycle.Service.Room.AppUserHasRouteService;
import com.Tesis.bicycle.Service.Room.RouteService;

@Database(entities = {Route.class, AppUserHasRoute.class}, version =5,exportSchema = true)
@TypeConverters({ConvertersDate.class})
public abstract class AppDataBase extends RoomDatabase {

    public abstract RouteService routeService();
    public abstract AppUserHasRouteService appUserHasRouteService();

}
