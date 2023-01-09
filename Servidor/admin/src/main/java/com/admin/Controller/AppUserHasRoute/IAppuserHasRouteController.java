package com.admin.Controller.AppUserHasRoute;

import com.admin.Dto.AppUserHasRoute.AppUserHasRouteDetailsDto;
import com.admin.Dto.AppUserHasRoute.AppUserHasRouteDto;
import com.admin.Dto.AppUserHasRoute.AppUserRouteRequestDto;
import com.admin.Dto.AppUserHasRoute.StatisticsDTO;
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
    Optional<AppUserHasRouteDto> getById(@RequestBody String id);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path={"/list"},produces = MediaType.APPLICATION_JSON_VALUE)
    List<AppUserHasRouteDto> getAppUserHasRoute();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path={"/getroutesbyuser"},produces=MediaType.APPLICATION_JSON_VALUE)
    AppUserHasRouteDetailsDto getRoutesByUser(@RequestParam(value="id") Long appUser);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/getstatisticsbyroute",produces = MediaType.APPLICATION_JSON_VALUE)
    List<StatisticsDTO> getStatisticsByRoute(@RequestParam("route") String routeId);



}
