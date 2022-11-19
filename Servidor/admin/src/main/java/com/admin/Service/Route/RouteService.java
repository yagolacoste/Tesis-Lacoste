package com.admin.Service.Route;

import com.admin.Controller.Exception.ErrorCodes;
import com.admin.Controller.Exception.TypeExceptions.NotFoundException;
import com.admin.Dto.Route.RouteNewRequestDto;
import com.admin.Models.Route;
import com.admin.Repository.IRouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        if(routeReal.getDescription()!=null)
            routeReal.setDescription(route.getDescription());

       routeReal.setWeather(route.getWeather());
        routeReal.setCoordinates(route.getCoordinates());
//        JsonObject jsonObject=new JsonParser().parse(route.getCoordinates()).getAsJsonObject();
        routeRepository.save(routeReal);
    }

    @Override
    public List<Route> list() {
        List<Route> result=routeRepository.findAll();
        if(result!=null)
            return result;
        return null;
    }


}
