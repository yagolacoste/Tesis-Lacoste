package com.Tesis.admin.Dto.Statistics;

import com.Tesis.admin.Dto.Route.RouteDetailsDto;

import java.io.Serializable;
import java.util.List;

public class RoutesDto implements Serializable {

    private Long userId;


    private List<RouteDetailsDto> routes;

    public RoutesDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<RouteDetailsDto> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteDetailsDto> routes) {
        this.routes = routes;
    }



}
