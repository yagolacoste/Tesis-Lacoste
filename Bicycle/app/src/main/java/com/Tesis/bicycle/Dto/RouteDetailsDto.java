package com.Tesis.bicycle.Dto;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RouteDetailsDto implements Serializable {


    private Long id;

    private String description;


    private String weather;


    private String coordinates;

    public RouteDetailsDto(Long id, String description, String weather, String coordinates) {
        this.id = id;
        this.description = description;
        this.weather = weather;
        this.coordinates = coordinates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }



    @Override
    public String toString() {
        return
                "description='" + description + '\'' +
                ", weather='" + weather + '\'' +
                ", coordinates='" + coordinates + '\'' ;
    }
}
