package com.admin.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="appuser_has_route")
public class AppUserHasRoute implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AppUserHasRouteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appuser_appuser", insertable = false, updatable = false)
    private User appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id_route", insertable = false, updatable = false)
    private Route route;

    @Column(name= "speed")
    private Double speed;

    @Column(name= "timespeed")
    private Double timeSpeed;

    @Column(name= "kilometres")
    private Double kilometres;

    @Column(name= "timesession")
    private Date timesession;


    public AppUserHasRoute() {
    }

    public AppUserHasRouteId getId() {
        return id;
    }

    public void setId(AppUserHasRouteId id) {
        this.id = id;
    }


    public User getAppUser() {
        return appUser;
    }

    public void setAppUser(User appUser) {
        this.appUser = appUser;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
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

    public Date getTimesession() {
        return timesession;
    }

    public void setTimesession(Date timesession) {
        this.timesession = timesession;
    }
}
