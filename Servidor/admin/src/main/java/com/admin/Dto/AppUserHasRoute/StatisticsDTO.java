package com.admin.Dto.AppUserHasRoute;

import com.admin.Models.AppUserHasRoute;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class StatisticsDTO implements Serializable {

    private float distance;

    private float speed;

    private LocalTime timeSpeed;

    @JsonFormat(timezone = "dd-MM-yyyy")
    private Date timeSession;

    private String weather;

    public StatisticsDTO() {
    }

    public StatisticsDTO(AppUserHasRoute appUserHasRoute){
        distance= appUserHasRoute.getDistance();
        speed=appUserHasRoute.getAvgSpeed();
        timeSpeed=appUserHasRoute.getTime();
        timeSession=appUserHasRoute.getTimeCreated();
        weather=appUserHasRoute.getWeather();
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public LocalTime getTimeSpeed() {
        return timeSpeed;
    }

    public void setTimeSpeed(LocalTime timeSpeed) {
        this.timeSpeed = timeSpeed;
    }

    public Date getTimeSession() {
        return timeSession;
    }

    public void setTimeSession(Date timeSession) {
        this.timeSession = timeSession;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
