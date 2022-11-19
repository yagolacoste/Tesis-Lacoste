package com.admin.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name="appuser_has_route")

public class AppUserHasRoute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id",columnDefinition = "serial")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appuser_appuser")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "route_id_route")
    private Route route;

    @Column(name= "speed")
    private Double speed;

    @Column(name= "timespeed")
    private Double timeSpeed;

    @Column(name= "kilometres")
    private Double kilometres;

    @Column(name= "timesession")
    private LocalDate timesession;


    public AppUserHasRoute() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDate getTimesession() {
        return timesession;
    }

    public void setTimesession(LocalDate timesession) {
        this.timesession = timesession;
    }
}
