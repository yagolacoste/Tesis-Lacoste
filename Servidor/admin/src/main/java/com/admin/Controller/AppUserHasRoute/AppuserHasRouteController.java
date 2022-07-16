package com.admin.Controller.AppUserHasRoute;

import com.admin.Dto.AppUserRouteRequestDto;
import com.admin.Models.AppuserHasRoute;
import com.admin.Models.AppuserHasRouteId;
import com.admin.Service.AppuserHasRoute.IAppuserHasRouteService;
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
    private IAppuserHasRouteService appuserHasRouteService;


    @Override
    public void addAppuserRoute(AppUserRouteRequestDto appUserRouteRequestDto) {
        appuserHasRouteService.add(appUserRouteRequestDto);
    }

    @Override
    public Optional<AppuserHasRoute> getById(AppuserHasRouteId id) {
        return appuserHasRouteService.getById(id);
    }

    @Override
    public List<AppuserHasRoute> getAppUserHasRoute() {
        return appuserHasRouteService.list();
    }
}
