package com.Tesis.admin.Controller.Statistics;


import com.Tesis.admin.Dto.Route.RouteDetailsDto;
import com.Tesis.admin.Dto.Statistics.RoutesDto;
import com.Tesis.admin.Dto.Statistics.StatisticsDto;
import com.Tesis.admin.Dto.Statistics.StatisticsRequestDto;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(IStatisticsController.PATH)
public interface IStatisticsController {

    static final String PATH ="/statistics";


    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path={"/add"},consumes = MediaType.APPLICATION_JSON_VALUE)
    void addAppUserRoute(@RequestBody StatisticsRequestDto statisticsRequestDto);


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path={"/get"})
    Optional<StatisticsDto> getById(@RequestBody String id);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path={"/list"},produces = MediaType.APPLICATION_JSON_VALUE)
    List<StatisticsDto> getStatistics();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path={"/getroutesbyuser"},produces=MediaType.APPLICATION_JSON_VALUE)
    List<RouteDetailsDto> getRoutesByUser(@RequestParam(value="id") Long appUser);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/getstatisticsbyroute",produces = MediaType.APPLICATION_JSON_VALUE)
    List<StatisticsDto> getStatisticsByRoute(@RequestParam("route") String routeId);



}
