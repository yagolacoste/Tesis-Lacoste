package com.admin.Dto;

import org.json.JSONObject;

import javax.validation.constraints.NotBlank;

public class RouteNewRequestDto {
    private String description;

    private String  weather;

    @NotBlank(message = "Coordinates is required")
    private String coordinates;

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
