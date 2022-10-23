package com.Tesis.bicycle.Model.ApiRest;


import java.util.Date;

public class AppUserHasRouteApiRest {


    private Long appUser;

    private Long route;

    private Double speed;

    private Double timeSpeed;

    private Double kilometres;

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



    public Double getKilometres() {
        return kilometres;
    }

    public void setKilometres(Double kilometres) {
        this.kilometres = kilometres;
    }

    public Double getTimeSpeed() {
        return timeSpeed;
    }

    public void setTimeSpeed(Double timeSpeed) {
        this.timeSpeed = timeSpeed;
    }

    public Date getTimesSession() {
        return timesSession;
    }

    public void setTimesSession(Date timesSession) {
        this.timesSession = timesSession;
    }
}
