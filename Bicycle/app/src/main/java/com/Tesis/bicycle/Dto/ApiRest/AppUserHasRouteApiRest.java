package com.Tesis.bicycle.Dto.ApiRest;


import com.fasterxml.jackson.annotation.JsonFormat;

import org.osmdroid.util.GeoPoint;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class AppUserHasRouteApiRest implements Serializable {


    private Long appUser;

    private String route;

    private String title;

    private String description;

    private float distance;

    private float avgSpeed;

    @JsonFormat(timezone = JsonFormat.DEFAULT_TIMEZONE,pattern = "H:mm:ss")
    private LocalTime time;

    private Date timeCreated;

    private String weather;

    private List<GeoPoint> coordinates;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(float avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public List<GeoPoint> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<GeoPoint> coordinates) {
        this.coordinates = coordinates;
    }
}
