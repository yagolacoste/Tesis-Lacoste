package com.Tesis.admin.Dto.Route;

import com.Tesis.admin.Models.Route;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import javax.persistence.Column;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.List;

public class RouteDetailsDto implements Serializable {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("coordinates")
    private List<GeoPoint> coordinates;

    @JsonProperty("distance")
    private float avgDistance;

    @JsonProperty("time")
    private LocalTime avgTime;

//    public RouteDetailsDto(String id, String description, String name, String coordinates) {
//        this.id = id;
//        this.description = description;
//        this.name = name;
//        Gson gson = new Gson();
//        Type listType = new TypeToken<List<GeoPoint>>() {}.getType();
//        this.coordinates = gson.fromJson(coordinates, listType);
//
//    }

    public RouteDetailsDto(Route r) {
        this.id = r.getId();
        this.description = r.getDescription();
        this.name = r.getName();
        this.avgDistance=r.getDistance();
        this.avgTime=r.getAvgProm();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<GeoPoint>>() {}.getType();
        this.coordinates = gson.fromJson(r.getCoordinates(), listType);

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
}
