package com.admin.Service.AppuserHasRoute;

import com.admin.Controller.Exception.ErrorCodes;
import com.admin.Controller.Exception.TypeExceptions.NotFoundException;
import com.admin.Dto.AppUserHasRoute.AppUserHasRouteDetailsDto;
import com.admin.Dto.AppUserHasRoute.AppUserRouteRequestDto;
import com.admin.Dto.Route.RouteDetailsDto;
import com.admin.Models.AppUserHasRoute;

import com.admin.Models.Route;
import com.admin.Models.User;
import com.admin.Repository.IAppUserHasRouteRepository;
import com.admin.Service.Route.IRouteService;
import com.admin.Service.User.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserHasRouteService implements IAppUserHasRouteService {

    private static final Logger LOG = LoggerFactory.getLogger(AppUserHasRouteService.class);

    @Autowired
    private IAppUserHasRouteRepository appUserHasRouteRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRouteService routeService;


    @Override
    public void add(AppUserRouteRequestDto appUserRouteRequestDto) {

        Route route=routeService.getById(appUserRouteRequestDto.getRoute());
        User appUser=userService.getByUser(appUserRouteRequestDto.getAppUser());

        AppUserHasRoute appUserHasRoute=new AppUserHasRoute();
        appUserHasRoute.setAppUser(appUser);
        appUserHasRoute.setRoute(route);
        appUserHasRoute.setSpeed(appUserRouteRequestDto.getSpeed());
        appUserHasRoute.setKilometres(appUserRouteRequestDto.getKilometres());
        appUserHasRoute.setTimeSpeed(appUserRouteRequestDto.getTimeSpeed());
        appUserHasRoute.setTimesession(appUserRouteRequestDto.getTimeSession());

        appUserHasRouteRepository.save(appUserHasRoute);

    }

    @Override
    public Optional<AppUserHasRoute> getById(Long id) {
        return Optional.ofNullable(appUserHasRouteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not exist user with route", ErrorCodes.NOT_FOUND.getCode())));
    }

    @Override
    public List<AppUserHasRoute> list() {
        List<AppUserHasRoute>result= appUserHasRouteRepository.findAll();
        return result;
    }

    @Override
    public AppUserHasRouteDetailsDto getRouteById(Long id) {
       List<RouteDetailsDto> result=appUserHasRouteRepository.findByRoute((long)id);

        User user= userService.getByUser(id);

        AppUserHasRouteDetailsDto appUserHasRouteDetailsDto= new AppUserHasRouteDetailsDto(user.getId(), user.getFirstName(),result);

        return appUserHasRouteDetailsDto;
    }
}
