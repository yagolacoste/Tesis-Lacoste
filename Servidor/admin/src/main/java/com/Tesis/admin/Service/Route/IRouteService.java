package com.Tesis.admin.Service.Route;

import com.Tesis.admin.Dto.Route.RouteNewRequestDto;
import com.Tesis.admin.Models.Route;

import java.util.List;


public interface IRouteService {
    Route getById(String id);

    void add(RouteNewRequestDto route);

    List<Route> list();

    boolean existsById(String id);

}
