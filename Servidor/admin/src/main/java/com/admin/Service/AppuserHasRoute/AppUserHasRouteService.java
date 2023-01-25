package com.admin.Service.AppuserHasRoute;

import com.admin.Controller.Exception.ErrorCodes;
import com.admin.Controller.Exception.TypeExceptions.NotFoundException;
import com.admin.Dto.AppUserHasRoute.AppUserHasRouteDetailsDto;
import com.admin.Dto.AppUserHasRoute.AppUserHasRouteDto;
import com.admin.Dto.AppUserHasRoute.AppUserRouteRequestDto;
import com.admin.Dto.AppUserHasRoute.StatisticsDTO;
import com.admin.Dto.Route.RouteDetailsDto;
import com.admin.Dto.Route.RouteNewRequestDto;
import com.admin.Dto.UserAppDto;
import com.admin.Models.AppUserHasRoute;

import com.admin.Models.Route;
import com.admin.Models.User;
import com.admin.Repository.IAppUserHasRouteRepository;
import com.admin.Service.Route.IRouteService;
import com.admin.Service.User.IUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        //Guarda nueva ruta
        if(appUserRouteRequestDto.getTitle()!=null) {
            RouteNewRequestDto routeNewRequestDto = new RouteNewRequestDto();
            routeNewRequestDto.setId(appUserRouteRequestDto.getRoute());
            routeNewRequestDto.setName(appUserRouteRequestDto.getTitle());
            routeNewRequestDto.setDescription(appUserRouteRequestDto.getDescription());
            routeNewRequestDto.setCoordinates(appUserRouteRequestDto.getCoordinates());
            routeService.add(routeNewRequestDto);
        }

        //Guarda nuevas estadisticas
        Route route=routeService.getById(appUserRouteRequestDto.getRoute());
        UserAppDto appUser=userService.getByUser(appUserRouteRequestDto.getAppUser());
        User u=new User();
        u.setId(appUser.getId());
        AppUserHasRoute appUserHasRoute=new AppUserHasRoute();
        appUserHasRoute.setId(RandomStringUtils.random(10,true,true));
        appUserHasRoute.setAppUser(u);///Transformar user
        appUserHasRoute.setRoute(route);
        appUserHasRoute.setDistance(appUserRouteRequestDto.getDistance());
        appUserHasRoute.setAvgSpeed(appUserRouteRequestDto.getAvgSpeed());
        appUserHasRoute.setTime(appUserRouteRequestDto.getTime());
        appUserHasRoute.setTimeCreated(appUserRouteRequestDto.getTimeCreated());
        appUserHasRouteRepository.save(appUserHasRoute);

    }

    @Override
    public Optional<AppUserHasRouteDto> getById(String id) {
        return Optional.ofNullable(appUserHasRouteRepository.findById(id).map(r->new AppUserHasRouteDto(r))
                .orElseThrow(() -> new NotFoundException("Not exist user with route", ErrorCodes.NOT_FOUND.getCode())));
    }

    @Override
    public List<AppUserHasRouteDto> list() {
        List<AppUserHasRouteDto>result= appUserHasRouteRepository.findAll().stream().map(r->new AppUserHasRouteDto(r)).collect(Collectors.toList());
        return result;
    }

    @Override
    public AppUserHasRouteDetailsDto getRoutesByUser(Long appUser) {
       List<RouteDetailsDto> result=appUserHasRouteRepository.findByRoute(appUser);
        UserAppDto user=userService.getByUser(appUser);
        AppUserHasRouteDetailsDto appUserHasRouteDetailsDto= new AppUserHasRouteDetailsDto();
        appUserHasRouteDetailsDto.setUserId(appUser);
        appUserHasRouteDetailsDto.setUserName(user.getUserName());
        appUserHasRouteDetailsDto.setRoutes(result);
        return appUserHasRouteDetailsDto;
    }

    @Override
    public List<StatisticsDTO> getStatisticsByRoute(String routeId) {
        return appUserHasRouteRepository.findAllStatisticsByRoute(routeId);
    }
}
