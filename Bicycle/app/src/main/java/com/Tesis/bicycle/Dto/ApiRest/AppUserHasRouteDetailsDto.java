package com.Tesis.bicycle.Dto.ApiRest;

import java.io.Serializable;
import java.util.List;

public class AppUserHasRouteDetailsDto implements Serializable {

    private Long userId;

    private String firstName;

    private Double distanceProm;

    private Double timeProm;

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

    public Double getDistanceProm() {
        return distanceProm;
    }

    public void setDistanceProm(Double distanceProm) {
        this.distanceProm = distanceProm;
    }

    public Double getTimeProm() {
        return timeProm;
    }

    public void setTimeProm(Double timeProm) {
        this.timeProm = timeProm;
    }
}
