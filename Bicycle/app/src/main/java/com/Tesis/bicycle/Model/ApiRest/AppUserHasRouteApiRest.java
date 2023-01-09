package com.Tesis.bicycle.Model.ApiRest;


import com.Tesis.bicycle.Converters.ConvertersDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class AppUserHasRouteApiRest implements Serializable {


    private Long appUser;

    private String route;

    private Double speed;

    private Double timeSpeed;

    private Double kilometres;

    private LocalDate timeSession;

    private String name;

    private String description;

    private String weather;

    private String coordinates;

    public AppUserHasRouteApiRest() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDate getTimeSession() {
        return timeSession;
    }

    public void setTimeSession(LocalDate timeSession) {
        this.timeSession = timeSession;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "AppUserHasRouteApiRest{" +
                "appUser=" + appUser +
                ", route='" + route + '\'' +
                ", speed=" + speed +
                ", timeSpeed=" + timeSpeed +
                ", kilometres=" + kilometres +
                ", timeSession=" + timeSession +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", weather='" + weather + '\'' +
                ", coordinates='" + coordinates + '\'' +
                '}';
    }
}
