package com.admin.Controller.AppUserHasRoute;

import com.admin.Dto.AppUserHasRoute.AppUserHasRouteDetailsDto;
import com.admin.Dto.AppUserHasRoute.AppUserRouteRequestDto;
import com.admin.Models.AppUserHasRoute;
import com.admin.Models.AppUserHasRouteId;
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
    void addAppUserRoute(@RequestBody AppUserRouteRequestDto appUserRouteRequestDto);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path={"/get"})
    Optional<AppUserHasRoute> getById(@RequestBody AppUserHasRouteId id);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path={"/list"},produces = MediaType.APPLICATION_JSON_VALUE)
    List<AppUserHasRoute> getAppUserHasRoute();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path={"/getroutesbyuser"},produces=MediaType.APPLICATION_JSON_VALUE)
    AppUserHasRouteDetailsDto getRouteById(@RequestParam(value="id") Long id);



}
