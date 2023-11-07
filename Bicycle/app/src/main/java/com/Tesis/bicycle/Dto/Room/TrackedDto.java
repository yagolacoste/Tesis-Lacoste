package com.Tesis.bicycle.Dto.Room;

import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import com.Tesis.bicycle.Converters.ConvertersDate;
import com.Tesis.bicycle.Converters.ListOfGeoPointConvert;
import com.Tesis.bicycle.Converters.LocalTimeConverter;
import com.Tesis.bicycle.Model.Room.Tracked;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.osmdroid.util.GeoPoint;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class TrackedDto implements Serializable {

    @JsonProperty("routeId")
    private String routeId;

    @JsonProperty( "title")
    private String title="";

    @JsonProperty( "description")
    private String description="";

    @JsonProperty("coordinates")
    @TypeConverters(ListOfGeoPointConvert.class)
    private List<GeoPoint> coordinates;

    //statistics
    @JsonProperty("appUser")
    private Long appUser;

    @JsonProperty("distance")
    private float distance=0;
    @JsonProperty("avgSpeed")
    private float avgSpeed=0;

    @JsonProperty("time")
   // @JsonFormat(timezone = JsonFormat.DEFAULT_TIMEZONE,pattern = "H:mm:ss")
    @TypeConverters(LocalTimeConverter.class)
    private LocalTime time;

    @JsonProperty("timeCreated")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "America/Buenos_Aires")
    @TypeConverters(ConvertersDate.class)
    private Date timeCreated;

    @JsonProperty("battle")
    private Long battle;//si se hizo una batalla

    public TrackedDto() {
    }

    public TrackedDto(Tracked tracked) {
        this.routeId = tracked.getRouteId();
        this.title = tracked.getTitle();
        this.description = tracked.getDescription();
        this.coordinates = tracked.getCoordinates();
        this.appUser = tracked.getAppUser();
        this.distance = tracked.getDistance();
        this.avgSpeed = tracked.getAvgSpeed();
        this.time = tracked.getTime();
        this.timeCreated = tracked.getTimeCreated();
        this.battle = tracked.getBattle();
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
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

    public List<GeoPoint> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<GeoPoint> coordinates) {
        this.coordinates = coordinates;
    }

    public Long getAppUser() {
        return appUser;
    }

    public void setAppUser(Long appUser) {
        this.appUser = appUser;
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

    public Long getBattle() {
        return battle;
    }

    public void setBattle(Long battle) {
        this.battle = battle;
    }
}
