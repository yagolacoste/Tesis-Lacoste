package com.Tesis.admin.Dto.Route;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;


public class RouteNewRequestDto implements Serializable {
    @NotBlank(message = "id is required")
    private String id;

    private String description;

    private String  name;

    @JsonProperty("coordinates")
    private List<GeoPoint> coordinates;

    private float distance;

    private LocalTime avgTime;

    private Long photo;

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

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public LocalTime getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(LocalTime avgTime) {
        this.avgTime = avgTime;
    }

    public Long getPhoto() {
        return photo;
    }

    public void setPhoto(Long photo) {
        this.photo = photo;
    }
}
