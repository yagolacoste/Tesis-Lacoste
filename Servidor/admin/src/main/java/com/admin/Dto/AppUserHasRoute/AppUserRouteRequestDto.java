package com.admin.Dto.AppUserHasRoute;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class AppUserRouteRequestDto {
    @NotBlank(message = "Is required")
    private Long AppUser;
    @NotBlank(message = "Is required")
    private Long route;
    @NotBlank(message = "Is required")
    private Double speed;
    @NotBlank(message = "Is required")
    private Double timeSpeed;
    @NotBlank(message = "Is required")
    private Double kilometres;
    @NotBlank(message = "Is required")
    private Date timeSession;


    public Long getAppUser() {
        return AppUser;
    }

    public void setAppUser(Long appUser) {
        AppUser = appUser;
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

    public Date getTimeSession() {
        return timeSession;
    }

    public void setTimeSession(Date timeSession) {
        this.timeSession = timeSession;
    }
}
