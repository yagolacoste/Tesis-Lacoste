package com.admin.Dto.Route;

import com.fasterxml.jackson.annotation.JsonRawValue;
import org.json.JSONObject;

import javax.persistence.Column;
import java.io.Serializable;

public class RouteDetailsDto implements Serializable {

    private String id;

    private String description;


    private String weather;


    private String coordinates;

    public RouteDetailsDto(String id, String description, String weather, String coordinates) {
        this.id = id;
        this.description = description;
        this.weather = weather;
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
