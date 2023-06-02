package com.Tesis.admin.Service.Route;


import com.Tesis.admin.Controller.Exception.TypeExceptions.NotFoundException;
import com.Tesis.admin.Dto.Route.RouteNewRequestDto;
import com.Tesis.admin.Exception.ErrorCodes;
import com.Tesis.admin.Models.Route;
import com.Tesis.admin.Repository.IRouteRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService implements IRouteService{

    private static final Logger LOG = LoggerFactory.getLogger(RouteService.class);

    @Autowired
    private IRouteRepository routeRepository;

    @Override
    public Route getById(String id) {

        Route route= routeRepository.findById(id).orElseThrow(() -> new NotFoundException("Router by id not found", ErrorCodes.NOT_FOUND.getCode()));
        return route;
    }

    @Override
    public void add(RouteNewRequestDto route) {
        Route routeReal= new Route();
        routeReal.setId(route.getId());
        routeReal.setDescription(route.getDescription());
        routeReal.setName(route.getName());
        Gson gson=new Gson();
        String json=gson.toJson(route.getCoordinates());
        routeReal.setCoordinates(json);
        routeRepository.save(routeReal);
    }

    @Override
    public List<Route> list() {
        List<Route> result=routeRepository.findAll();
        if(result!=null)
            return result;
        return null;
    }

    @Override
    public boolean existsById(String id) {
        return routeRepository.existsById(id);
    }


}
