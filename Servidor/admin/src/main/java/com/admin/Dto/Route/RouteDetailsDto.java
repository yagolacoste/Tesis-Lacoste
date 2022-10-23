package com.admin.Dto.Route;

import com.fasterxml.jackson.annotation.JsonRawValue;
import org.json.JSONObject;

import javax.persistence.Column;

public class RouteDetailsDto {


    private String description;


    private String weather;


    private String coordinates;

    public RouteDetailsDto(String description, String weather, String coordinates) {
        this.description = description;
        this.weather = weather;
        this.coordinates = coordinates;
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
}
