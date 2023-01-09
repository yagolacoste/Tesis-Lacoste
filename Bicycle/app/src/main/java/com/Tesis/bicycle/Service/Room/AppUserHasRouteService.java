package com.Tesis.bicycle.Service.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.Tesis.bicycle.Dto.Room.AppUserHasRouteDTO;
import com.Tesis.bicycle.Dto.Room.RouteDTO;
import com.Tesis.bicycle.Model.Room.AppUserHasRoute;

import java.util.List;

@Dao
public interface AppUserHasRouteService {

    @Insert
    void addAppUserHasRoute(AppUserHasRoute appUserHasRoute);

    @Query("SELECT * FROM APPUSER_HAS_ROUTE where id=:id")
    AppUserHasRouteDTO getAppUserHasRouteByID(String id);

    @Query("Select Count(*) from APPUSER_HAS_ROUTE")
    int countAppUserHasRoute();

    @Query("SELECT * FROM APPUSER_HAS_ROUTE")
    List<AppUserHasRouteDTO> getAll();

//    @Query("SELECT speed,timeSpeed,kilometres,timesSession from AppUserHasRoute where (appUser=:appUser and route=:route)")
//    InfomationDto getInformation(Long appUser,Long route);
}
