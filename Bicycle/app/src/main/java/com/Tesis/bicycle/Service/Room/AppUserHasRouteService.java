package com.Tesis.bicycle.Service.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.Tesis.bicycle.Dto.InfomationDto;
import com.Tesis.bicycle.Model.Room.AppUserHasRoute;

@Dao
public interface AppUserHasRouteService {

    @Insert
    void addInformation(AppUserHasRoute appUserHasRoute);

    @Query("SELECT speed,timeSpeed,kilometres,timesSession from AppUserHasRoute where (appUser=:appUser and route=:route)")
    InfomationDto getInformation(Long appUser,Long route);
}
