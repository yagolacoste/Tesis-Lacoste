package com.admin.Dto;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class AppUserRouteRequestDto {
    @NotBlank(message = "Is required")
    private Long Appuser;
    @NotBlank(message = "Is required")
    private Long route;
    @NotBlank(message = "Is required")
    private Double speed;
    @NotBlank(message = "Is required")
    private Date timespeeed;
    @NotBlank(message = "Is required")
    private Double kilometres;
    @NotBlank(message = "Is required")
    private Date timesession;

    //Route data

    private String description;

    private String weather;

    @NotBlank(message = "Is required")
    private String coordinates;


    public Long getAppuser() {
        return Appuser;
    }

    public void setAppuser(Long appuser) {
        Appuser = appuser;
    }

    public Long getRoute() {
        return route;
    }

    public void setRoute(Long route) {
        this.route = route;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Date getTimeSpeeed() {
        return timespeeed;
    }

    public void setTimeSpeeed(Date timeSpeeed) {
        this.timespeeed = timeSpeeed;
    }

    public Double getKilometres() {
        return kilometres;
    }

    public void setKilometres(Double kilometres) {
        this.kilometres = kilometres;
    }

    public Date getTimeSession() {
        return timesession;
    }

    public void setTimeSession(Date timeSession) {
        this.timesession = timeSession;
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
