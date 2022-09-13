package com.admin.Service.Route;

import com.admin.Controller.Exception.ErrorCodes;
import com.admin.Controller.Exception.TypeExceptions.NotFoundException;
import com.admin.Dto.RouteNewRequestDto;
import com.admin.Models.Route;
import com.admin.Repository.IRouteRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService implements IRouteService{

    private static final Logger LOG = LoggerFactory.getLogger(RouteService.class);

    @Autowired
    private IRouteRepository routeRepository;

    @Override
    public Optional<Route> getById(Long id) {

        return Optional.ofNullable(routeRepository.findById(id).orElseThrow(() -> new NotFoundException("Router by id not found", ErrorCodes.NOT_FOUND.getCode())));
    }

    @Override
    public void add(RouteNewRequestDto route) {
        Route routeReal= new Route();
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
