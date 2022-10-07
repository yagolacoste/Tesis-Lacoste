package com.Tesis.bicycle.Model.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Date;


@Entity(tableName="AppUserHasRoute",primaryKeys = {"appUser","route"})
public class AppUserHasRoute {


    @NotNull
    private Long appUser;


    @NotNull
    private Long route;

    @ColumnInfo(name= "speed")
    private Double speed;

    @ColumnInfo(name= "timeSpeed")
    private Double timeSpeed;

    @ColumnInfo(name= "kilometres")
    private Double kilometres;

    @ColumnInfo(name="timesSession")
    private Date timesSession;

    public Long getAppUser() {
        return appUser;
    }

    public void setAppUser(Long appUser) {
        this.appUser = appUser;
    }

    public Long getRoute() {
        return route;
    }

    public void setRoute(Long route) {
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

    public Date getTimesSession() {
        return timesSession;
    }

    public void setTimesSession(Date timesSession) {
        this.timesSession = timesSession;
    }
}
