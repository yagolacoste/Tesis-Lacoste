package com.admin.Service.Route;

import com.admin.Dto.Route.RouteNewRequestDto;
import com.admin.Models.Route;

import java.util.List;
import java.util.Optional;


public interface IRouteService {
    Optional<Route> getById(Long id);

    void add(RouteNewRequestDto route);

    List<Route> list();
}
