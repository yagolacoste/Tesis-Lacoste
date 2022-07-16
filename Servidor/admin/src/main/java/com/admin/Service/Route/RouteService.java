package com.admin.Service.Route;

import com.admin.Models.Route;
import com.admin.Repository.IRouteRepository;
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
        return routeRepository.findById(id);
    }

    @Override
    public void add(Route route) {
        routeRepository.save(route);
    }

    @Override
    public List<Route> list() {
        List<Route> result=routeRepository.findAll();
        return result;
    }
}
