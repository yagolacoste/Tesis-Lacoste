package com.admin.Service.Route;

import com.admin.Dto.Route.RouteNewRequestDto;
import com.admin.Models.Route;

import java.util.List;


public interface IRouteService {
    Route getById(String id);

    void add(RouteNewRequestDto route);

    List<Route> list();


}
