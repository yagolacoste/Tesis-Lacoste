package com.Tesis.admin.Service.Route;


import com.Tesis.admin.Controller.Exception.TypeExceptions.NotFoundException;
import com.Tesis.admin.Dto.Route.GeoPoint;
import com.Tesis.admin.Dto.Route.RouteNewRequestDto;
import com.Tesis.admin.Exception.ErrorCodes;
import com.Tesis.admin.Models.Route;
import com.Tesis.admin.Repository.IRouteRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService implements IRouteService{

    private static final Logger LOG = LoggerFactory.getLogger(RouteService.class);

    @Autowired
    private IRouteRepository routeRepository;

    @Override
    public Route getById(String id) {


        if(routeRepository.findById(id).isPresent()){
            return routeRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public void add(RouteNewRequestDto route) {
        Route routeReal= new Route();
        routeReal.setId(route.getId());
        routeReal.setDescription(route.getDescription());
        routeReal.setName(route.getName());
        routeReal.setDistance(route.getDistance());
        routeReal.setAvgProm(route.getAvgTime());
       // List<GeoPoint>points=route.getCoordinates().stream(r->new GeoPoint(r)).collect(Collectors.toList());
       List<GeoPoint> geoPoints= route.getCoordinates();

//            for (GeoPoint geoPoint : geoPoints) {
////                coordinates.add(new JsonPrimitive(geoPoint.getLatitude()));
////                coordinates.add(new JsonPrimitive(geoPoint.getLongitude()));
//                jsonArray.add(geoPoint);
//
//        }

        Gson gson=new Gson();
        String json=gson.toJson(geoPoints);
        routeReal.setCoordinates(json);
        routeRepository.save(routeReal);
    }

    @Override
    public List<Route> list() {

        return routeRepository.findAll();
    }

    @Override
    public boolean existsById(String id) {
        return routeRepository.existsById(id);
    }


}
