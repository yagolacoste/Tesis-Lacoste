package com.Tesis.bicycle.Model;

import org.json.JSONObject;

public class RouteApiRest {

    private String description;

    private String weather;

    private String coordinates;

    public RouteApiRest() {
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
