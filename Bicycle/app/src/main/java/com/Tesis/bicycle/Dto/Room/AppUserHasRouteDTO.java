package com.Tesis.bicycle.Dto.Room;

import androidx.room.ColumnInfo;

import java.io.Serializable;
import java.time.LocalDate;

public class AppUserHasRouteDTO implements Serializable {

    private String id;

    private Long appUser;

    private String route;

    private Double speed;

    private Double timeSpeed;

    private Double kilometres;

    private LocalDate timesSession;

    public AppUserHasRouteDTO() {
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

    public void setRoute(String route) {
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
}
