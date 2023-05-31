package com.Tesis.admin.Dto.Statistics;



import com.Tesis.admin.Models.Statistics;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

public class StatisticsDto implements Serializable {

    private String id;

    private Long appUser;

    private String route;

    private String weather;

    private float avgSpeed;


    private LocalTime time;


    private float distance;


    private Date timeCreated;

    public StatisticsDto() {
    }

    public StatisticsDto(Statistics r) {
        this.id=r.getId();
        this.appUser=r.getAppUser().getId();
        this.route=r.getRoute().getId();
        this.weather=r.getWeather();
        this.avgSpeed=r.getAvgSpeed();
        this.time=r.getTime();
        this.distance=r.getDistance();
        this.timeCreated=r.getTimeCreated();
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

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }
}
