package com.Tesis.admin.Controller.AppUserHasRoute;


import com.Tesis.admin.Dto.AppUserHasRoute.AppUserHasRouteDetailsDto;
import com.Tesis.admin.Dto.AppUserHasRoute.AppUserHasRouteDto;
import com.Tesis.admin.Dto.AppUserHasRoute.AppUserRouteRequestDto;
import com.Tesis.admin.Dto.AppUserHasRoute.StatisticsDTO;
import com.Tesis.admin.Service.AppuserHasRoute.IAppUserHasRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class AppuserHasRouteController implements IAppuserHasRouteController{
    private static final Logger LOG = LoggerFactory.getLogger(AppuserHasRouteController.class);


    @Autowired
    private IAppUserHasRouteService appUserHasRouteService;


    @Override
    public void addAppUserRoute(AppUserRouteRequestDto appUserRouteRequestDto) {
        appUserHasRouteService.add(appUserRouteRequestDto);
    }

    @Override
    public Optional<AppUserHasRouteDto> getById(String id) {
        return appUserHasRouteService.getById(id);
    }

    @Override
    public List<AppUserHasRouteDto> getAppUserHasRoute() {
        return appUserHasRouteService.list();
    }

    @Override
    public AppUserHasRouteDetailsDto getRoutesByUser(Long appUser) {
        return appUserHasRouteService.getRoutesByUser(appUser);
    }

    @Override
    public List<StatisticsDTO> getStatisticsByRoute(String routeId) {
        return appUserHasRouteService.getStatisticsByRoute(routeId);
    }
}
