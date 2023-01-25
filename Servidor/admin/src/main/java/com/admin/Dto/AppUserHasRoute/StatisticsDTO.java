package com.admin.Dto.AppUserHasRoute;

import com.admin.Models.AppUserHasRoute;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDate;

public class StatisticsDTO implements Serializable {

    private Double distance;

    private Double speed;

    private Double timeSpeed;

    private LocalDate timeSession;

    private String weather;

    public StatisticsDTO() {
    }

    public StatisticsDTO(AppUserHasRoute appUserHasRoute){
//        distance= appUserHasRoute.getDistance();
//        speed=appUserHasRoute.getAvgSpeed();
//        timeSpeed=appUserHasRoute.get();
//        timeSession=appUserHasRoute.getTimesession();
//        weather=appUserHasRoute.getWeather();
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
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

    public LocalDate getTimeSession() {
        return timeSession;
    }

    public void setTimeSession(LocalDate timeSession) {
        this.timeSession = timeSession;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
