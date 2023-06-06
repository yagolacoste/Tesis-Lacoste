package com.Tesis.bicycle.Dto.ApiRest;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.osmdroid.util.GeoPoint;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

public class RouteDetailsDto implements Serializable {


    private String id;

    private String description;

    private String name;

    private List<GeoPoint> coordinates;

    private float avgDistance;

    private LocalTime avgTime;

    public RouteDetailsDto(String id, String description, String name, List<GeoPoint> coordinates) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.coordinates = coordinates;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GeoPoint> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<GeoPoint> coordinates) {
        this.coordinates = coordinates;
    }

    public float getAvgDistance() {
        return avgDistance;
    }

    public void setAvgDistance(float avgDistance) {
        this.avgDistance = avgDistance;
    }

    public LocalTime getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(LocalTime avgTime) {
        this.avgTime = avgTime;
    }



    @Override
    public String toString() {
        return "RouteDetailsDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", coordinates='" + coordinates + '\'' +
                '}';
    }
}
