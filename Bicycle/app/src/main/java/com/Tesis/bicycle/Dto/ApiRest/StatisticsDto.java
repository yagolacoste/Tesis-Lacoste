package com.Tesis.bicycle.Dto.ApiRest;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class StatisticsDto implements Serializable {

   private float distance;

   private float speed;

   @JsonFormat(timezone = JsonFormat.DEFAULT_TIMEZONE,pattern = "H:mm:ss")
   private LocalTime timeSpeed;

   private Date timeSession;

   private String weather;

    public StatisticsDto() {
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
