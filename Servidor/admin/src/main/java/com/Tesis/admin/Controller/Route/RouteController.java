package com.Tesis.admin.Controller.Route;

import com.Tesis.admin.Dto.Route.RouteNewRequestDto;
import com.Tesis.admin.Models.Route;
import com.Tesis.admin.Service.Route.IRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RouteController implements IRouteController{

    private static final Logger LOG = LoggerFactory.getLogger(RouteController.class);

    @Autowired
    private IRouteService routeService;

    @Override
    public Route getById(String id) {
        return routeService.getById(id);
    }

    @Override
    public void addRoute(RouteNewRequestDto route) {
        routeService.add(route);
    }

    @Override
    public List<Route> getRoutes() {
        return routeService.list();
    }


}
