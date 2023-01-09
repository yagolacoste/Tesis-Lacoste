package com.admin.Dto.AppUserHasRoute;

import com.admin.Dto.Route.RouteDetailsDto;
import com.admin.Models.AppUserHasRoute;

import java.io.Serializable;
import java.util.List;

public class AppUserHasRouteDetailsDto implements Serializable {

    private Long userId;

    private String userName;

    private List<RouteDetailsDto> routes;

    public AppUserHasRouteDetailsDto() {
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<RouteDetailsDto> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteDetailsDto> routes) {
        this.routes = routes;
    }



}
