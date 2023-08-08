package com.Tesis.admin.Dto.Statistics;

import com.Tesis.admin.Convert.GeoPointListDeserializer;
import com.Tesis.admin.Convert.GeoPointListSerializer;
import com.Tesis.admin.Dto.Route.GeoPoint;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;


public class StatisticsRequestDto implements Serializable {

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

    @JsonFormat(pattern = "H:mm:ss")
    private LocalTime time;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "America/Buenos_Aires")
    private Date timeCreated;

    @JsonProperty("weather")
    private String weather;

    @JsonProperty("coordinates")
    @JsonSerialize(using = GeoPointListSerializer.class)
    @JsonDeserialize(using = GeoPointListDeserializer.class)
    private List<GeoPoint> coordinates;

    @JsonProperty("battleId")
    private Long battleId;

    @JsonProperty("image")
    private Long image;


    public StatisticsRequestDto() {
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

//    public String getCoordinates() {
//        return coordinates;
//    }
//
//    public void setCoordinates(String coordinates) {
//        this.coordinates = coordinates;
//    }


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



    public Long getImage() {

        return image;
    }



    public void setImage(Long image) {

        this.image = image;
    }
}
