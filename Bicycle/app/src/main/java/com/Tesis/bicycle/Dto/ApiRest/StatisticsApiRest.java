package com.Tesis.bicycle.Dto.ApiRest;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.osmdroid.util.GeoPoint;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class StatisticsApiRest implements Serializable {

    @JsonProperty("appUser")
    private Long appUser;

    @JsonProperty("route")
    private String route;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("distance")
    private float distance;

    @JsonProperty("avgSpeed")
    private float avgSpeed;

    @JsonFormat(timezone = JsonFormat.DEFAULT_TIMEZONE,pattern = "H:mm:ss")
    private LocalTime time;

    @JsonProperty("timeCreated")
    private Date timeCreated;

    @JsonProperty("weather")
    private String weather;

    @JsonProperty("coordinates")
    private List<GeoPoint> coordinates;

    @JsonProperty("battleId")
    private Long battleId;

    public StatisticsApiRest() {
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


    public Long getBattleId() {
        return battleId;
    }

    public void setBattleId(Long battleId) {
        this.battleId = battleId;
    }
}
