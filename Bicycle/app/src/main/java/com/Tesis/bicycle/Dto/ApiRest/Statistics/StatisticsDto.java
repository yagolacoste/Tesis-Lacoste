package com.Tesis.bicycle.Dto.ApiRest.Statistics;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;

public class StatisticsDto implements Serializable {

    @JsonProperty("id")
    private String id;

    @JsonProperty("appUser")
    private Long appUser;

    @JsonProperty("nameComplete")
    private String nameComplete;

    @JsonProperty("fileName")
    private String fileName;

    @JsonProperty("route")
    private String route;

    @JsonProperty("weather")
    private String weather;

    @JsonProperty("avgSpeed")
    private float avgSpeed;

    @JsonProperty("time")
    @JsonFormat(timezone = JsonFormat.DEFAULT_TIMEZONE,pattern = "H:mm:ss")
    private LocalTime time;

    @JsonProperty("distance")
    private float distance;

    @JsonProperty("timeCreated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "America/Buenos_Aires")
    private Date timeCreated;

    private boolean expandable=false;

    public StatisticsDto() {
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

    public String getTimeCreated() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(timeCreated);
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public String getNameComplete() {
        return nameComplete;
    }

    public void setNameComplete(String nameComplete) {
        this.nameComplete = nameComplete;
    }

    public String getDistanceString(){return distanceToString(distance,true);}
    public static String distanceToString(float distance, boolean b) {
        int metres = Math.round(distance);
        int km = metres / 1000;
        metres = metres % 1000;
        String format = "%d";

        if (km == 0) {
            if (b) {
                format += "m";
            }
            return String.format(Locale.UK, format, metres);
        }

        metres = metres/10;
        format = "%d.%02d";
        if (b) {
            format += "km";
        }
        return String.format(Locale.UK, format, km, metres);
    }

    public String getAvgSpeedString(){return String.format(Locale.UK, "%.2f", avgSpeed);}

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
