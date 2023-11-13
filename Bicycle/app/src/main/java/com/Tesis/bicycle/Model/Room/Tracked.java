package com.Tesis.bicycle.Model.Room;

import android.location.Location;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.Tesis.bicycle.Converters.ConvertersDate;
import com.Tesis.bicycle.Converters.ListOfGeoPointConvert;
import com.Tesis.bicycle.Converters.ListTypeConverter;
import com.Tesis.bicycle.Converters.LocalTimeConverter;
import com.Tesis.bicycle.Dto.ApiRest.Statistics.StatisticsApiRest;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.jetbrains.annotations.NotNull;
import org.osmdroid.util.GeoPoint;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName="tracked")
public class Tracked {

    @PrimaryKey(autoGenerate = true)
    private long id;
    //data route
    @ColumnInfo(name = "routeId")
    private String routeId;

    @ColumnInfo(name = "title")
    private String title="";

    @ColumnInfo(name = "description")
    private String description="";

    @ColumnInfo(name="coordinates")
    @TypeConverters(ListOfGeoPointConvert.class)
    private List<GeoPoint> coordinates;

    //statistics
    @ColumnInfo(name="appUser")
    private Long appUser;

    @ColumnInfo(name="distance")
    private float distance=0;
    @ColumnInfo(name="avgSpeed")
    private float avgSpeed=0;

    @ColumnInfo(name="time")
    //@JsonFormat(timezone = JsonFormat.DEFAULT_TIMEZONE,pattern = "H:mm:ss")
    @TypeConverters(LocalTimeConverter.class)
    private LocalTime time;

    @ColumnInfo(name="timeCreated")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "America/Buenos_Aires")
    @TypeConverters(ConvertersDate.class)
    private Date timeCreated;

    @ColumnInfo(name="battle")
    private Long battle;//si se hizo una batalla



    public Tracked() {
    }

    public Tracked(StatisticsApiRest statisticsApiRest) {
        this.routeId = statisticsApiRest.getRoute();
        this.title = statisticsApiRest.getTitle();
        this.description = statisticsApiRest.getDescription();
        this.coordinates = statisticsApiRest.getCoordinates();
        this.appUser = statisticsApiRest.getAppUser();
        this.distance = statisticsApiRest.getDistance();
        this.avgSpeed = statisticsApiRest.getAvgSpeed();
        this.time = statisticsApiRest.getTime();
        this.timeCreated = statisticsApiRest.getTimeCreated();
        this.battle = statisticsApiRest.getBattleId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
