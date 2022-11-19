package com.Tesis.bicycle.Service.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.Tesis.bicycle.Model.Room.AppUserHasRoute;

@Dao
public interface AppUserHasRouteService {

    @Insert
    void addAppUserHasRoute(AppUserHasRoute appUserHasRoute);

    @Query("SELECT * FROM AppUserHasRoute where id=:id")
    AppUserHasRoute getAppUserHasRouteByID(long id);

    @Query("Select Count(*) from AppUserHasRoute")
    int countAppUserHasRoute();

//    @Query("SELECT speed,timeSpeed,kilometres,timesSession from AppUserHasRoute where (appUser=:appUser and route=:route)")
//    InfomationDto getInformation(Long appUser,Long route);
}
