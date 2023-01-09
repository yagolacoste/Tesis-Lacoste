package com.admin.Dto.AppUserHasRoute;

import com.admin.Models.AppUserHasRoute;

import java.io.Serializable;
import java.time.LocalDate;

public class AppUserHasRouteDto implements Serializable {

    private String id;

    private Long appUser;

    private String route;

    private String weather;

    private Double speed;


    private Double timeSpeed;


    private Double kilometres;


    private LocalDate timeSession;

    public AppUserHasRouteDto() {
    }

    public AppUserHasRouteDto(AppUserHasRoute r) {
        this.id=r.getId();
        this.appUser=r.getAppUser().getId();
        this.route=r.getRoute().getId();
        this.weather=r.getWeather();
        this.speed=r.getSpeed();
        this.timeSpeed=r.getTimeSpeed();
        this.kilometres=r.getKilometres();
        this.timeSession=r.getTimesession();
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

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
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

    public LocalDate getTimeSession() {
        return timeSession;
    }

    public void setTimeSession(LocalDate timeSession) {
        this.timeSession = timeSession;
    }
}
