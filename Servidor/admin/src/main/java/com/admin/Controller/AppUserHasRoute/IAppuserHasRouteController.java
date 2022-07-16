package com.admin.Controller.AppUserHasRoute;

import com.admin.Dto.AppUserRouteRequestDto;
import com.admin.Models.AppuserHasRoute;
import com.admin.Models.AppuserHasRouteId;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(IAppuserHasRouteController.PATH)
public interface IAppuserHasRouteController {

    static final String PATH ="/appuserroute";


    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path={"/add"},consumes = MediaType.APPLICATION_JSON_VALUE)
    void addAppuserRoute(@RequestBody AppUserRouteRequestDto appUserRouteRequestDto);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path={"/get"})
    Optional<AppuserHasRoute> getById(@RequestBody AppuserHasRouteId id);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path={"/list"},produces = MediaType.APPLICATION_JSON_VALUE)
    List<AppuserHasRoute> getAppUserHasRoute();



}
