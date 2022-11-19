package com.admin.Dto.AppUserHasRoute;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class AppUserRouteRequestDto implements Serializable {

    private Long AppUser;

    private String route;

    private Double speed;

    private Double timeSpeed;

    private Double kilometres;

    private LocalDate timeSession;

    private String description;

    private String weather;

    private String coordinates;


    public Long getAppUser() {
        return AppUser;
    }

    public void setAppUser(Long appUser) {
        AppUser = appUser;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getTimeSpeed() {
        return timeSpeed;
    }

    public void setTimeSpeed(Double timeSpeed) {
        this.timeSpeed = timeSpeed;
    }

    public Double getKilometres() {
        return kilometres;
    }

    public void setKilometres(Double kilometres) {
        this.kilometres = kilometres;
    }

    public LocalDate getTimeSession() {
        return timeSession;
    }

    public void setTimeSession(LocalDate timeSession) {
        this.timeSession = timeSession;
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
