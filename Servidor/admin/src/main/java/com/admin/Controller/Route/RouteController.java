package com.admin.Controller.Route;

import com.admin.Controller.User.UserController;
import com.admin.Models.Route;
import com.admin.Service.Route.IRouteService;
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
    public Route getById(Long id) {
        return routeService.getById(id);
    }

    @Override
    public void addRoute(Route route) {
        routeService.add(route);
    }

    @Override
    public List<Route> getRoutes() {
        return routeService.list();
    }
}
