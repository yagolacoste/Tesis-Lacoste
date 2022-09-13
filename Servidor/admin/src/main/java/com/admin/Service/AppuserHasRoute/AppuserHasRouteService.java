package com.admin.Service.AppuserHasRoute;

import com.admin.Controller.Exception.ErrorCodes;
import com.admin.Controller.Exception.TypeExceptions.NotFoundException;
import com.admin.Dto.AppUserRouteRequestDto;
import com.admin.Models.AppuserHasRoute;
import com.admin.Models.AppuserHasRouteId;
import com.admin.Models.Route;
import com.admin.Repository.IAppuserHasRouteRepository;
import com.admin.Repository.IRouteRepository;
import com.admin.Service.Route.IRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppuserHasRouteService implements IAppuserHasRouteService{

    private static final Logger LOG = LoggerFactory.getLogger(AppuserHasRouteService.class);

    @Autowired
    private IAppuserHasRouteRepository appuserHasRouteRepository;

    @Autowired
    private IRouteRepository routeRepository;


    @Override
    public void add(AppUserRouteRequestDto appUserRouteRequestDto) {
        Route route =new Route();
        if(appUserRouteRequestDto.getDescription()!=null)
            route.setDescription(appUserRouteRequestDto.getDescription());
        if(appUserRouteRequestDto.getWeather()!=null)
          //  route.setWeather(appUserRouteRequestDto.getWeather());
       // route.setCoordinates(appUserRouteRequestDto.getCoordinates());
        routeRepository.save(route);


        AppuserHasRouteId appuserHasRouteId=new AppuserHasRouteId();
        appuserHasRouteId.setAppuser(appUserRouteRequestDto.getAppuser());
        appuserHasRouteId.setRoute(appUserRouteRequestDto.getRoute());
        AppuserHasRoute appuserHasRoute=new AppuserHasRoute();
        appuserHasRoute.setId(appuserHasRouteId);
        appuserHasRoute.setSpeed(appUserRouteRequestDto.getSpeed());
        appuserHasRoute.setKilometres(appUserRouteRequestDto.getKilometres());
        appuserHasRoute.setTimeSpeed(appUserRouteRequestDto.getTimeSpeeed());
        appuserHasRoute.setTimesession(appUserRouteRequestDto.getTimeSession());

        appuserHasRouteRepository.save(appuserHasRoute);

    }

    @Override
    public Optional<AppuserHasRoute> getById(AppuserHasRouteId id) {
        return Optional.ofNullable(appuserHasRouteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not exist user with route", ErrorCodes.NOT_FOUND.getCode())));
    }

    @Override
    public List<AppuserHasRoute> list() {
        return appuserHasRouteRepository.findAll();
    }
}
