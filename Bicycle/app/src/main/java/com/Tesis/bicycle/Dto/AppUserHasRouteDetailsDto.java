package com.Tesis.bicycle.Dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppUserHasRouteDetailsDto {

    private Long userId;


    private String firstName;


    private List<RouteDetailsDto> routes;

    public AppUserHasRouteDetailsDto(Long userId, String firstName, List<RouteDetailsDto> routes) {
        this.userId = userId;
        this.firstName = firstName;
        this.routes = routes;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<RouteDetailsDto> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteDetailsDto> routes) {
        this.routes = routes;
    }
}
