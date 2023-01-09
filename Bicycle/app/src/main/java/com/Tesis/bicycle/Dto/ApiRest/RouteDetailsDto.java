package com.Tesis.bicycle.Dto.ApiRest;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RouteDetailsDto implements Serializable {


    private String id;

    private String description;

    private String name;

    private String coordinates;

    public RouteDetailsDto(String id, String description, String name, String coordinates) {
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

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
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
