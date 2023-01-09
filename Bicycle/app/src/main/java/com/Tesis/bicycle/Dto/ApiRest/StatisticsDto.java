package com.Tesis.bicycle.Dto.ApiRest;

import java.io.Serializable;
import java.time.LocalDate;

public class StatisticsDto implements Serializable {

   private Double distance;

   private Double speed;

   private Double timeSpeed;

   private LocalDate timeSession;

   private String weather;

    public StatisticsDto() {
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

    @Override
    public String toString() {
        return "StatisticsDto{" +
                "distance=" + distance +
                ", speed=" + speed +
                ", timeSpeed=" + timeSpeed +
                ", timeSession=" + timeSession +
                ", weather='" + weather + '\'' +
                '}';
    }
}
