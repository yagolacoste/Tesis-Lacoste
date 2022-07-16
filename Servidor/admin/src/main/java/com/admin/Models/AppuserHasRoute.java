package com.admin.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="appuser_has_route")
public class AppuserHasRoute implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AppuserHasRouteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appuser_appuser_id", insertable = false, updatable = false)
    private User appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_route_id", insertable = false, updatable = false)
    private Route route;

    @Column(name= "speed")
    private Double speed;

    @Column(name= "timespeed")
    private Date timeSpeed;

    @Column(name= "kilometres")
    private Double kilometres;

    @Column(name= "timesession")
    private Date timesession;



    public AppuserHasRouteId getId() {
        return id;
    }

    public void setId(AppuserHasRouteId id) {
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

    public Date getTimeSpeed() {
        return timeSpeed;
    }

    public void setTimeSpeed(Date timeSpeed) {
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
