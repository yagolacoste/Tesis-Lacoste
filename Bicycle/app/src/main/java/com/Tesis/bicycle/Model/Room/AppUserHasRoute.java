package com.Tesis.bicycle.Model.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.Tesis.bicycle.Constants;

import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;


@Entity(tableName="appuser_has_route")
public class AppUserHasRoute {

    @PrimaryKey
    @NotNull
    @ColumnInfo(name="id")
    private String id;

    @ColumnInfo(name="appUser")
    private Long appUser;

    @ColumnInfo(name="route")
    private String route;

    @ColumnInfo(name= "speed")
    private Double speed;

    @ColumnInfo(name= "timeSpeed")
    private Double timeSpeed;

    @ColumnInfo(name= "kilometres")
    private Double kilometres;

    @ColumnInfo(name= "weather")
    private String weather;

    @ColumnInfo(name="timesSession")
    private LocalDate timesSession;

    public AppUserHasRoute() {
        this.id = RandomStringUtils.random(Constants.MAX_CARACTER_ID,true,true);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getAppUser() {
        return appUser;
    }

    public void setAppUser(Long appUser) {
        this.appUser = appUser;
    }


    public String getRoute() {
        return route;
    }

    public void setRoute(@NotNull String route) {
        this.route = route;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getTimeSpeed() {
        return timeSpeed;
    }

    public void setTimeSpeed(Double timeSpeed) {
        this.timeSpeed = timeSpeed;
    }

    public Double getKilometres() {
        return kilometres;
    }

    public void setKilometres(Double kilometres) {
        this.kilometres = kilometres;
    }

    public LocalDate getTimesSession() {
        return timesSession;
    }

    public void setTimesSession(LocalDate timesSession) {
        this.timesSession = timesSession;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
