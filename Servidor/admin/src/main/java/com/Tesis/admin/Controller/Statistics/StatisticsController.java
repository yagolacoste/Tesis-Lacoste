package com.Tesis.admin.Controller.Statistics;


import com.Tesis.admin.Dto.Route.RouteDetailsDto;
import com.Tesis.admin.Dto.Statistics.RoutesDto;

import com.Tesis.admin.Dto.Statistics.StatisticsDto;
import com.Tesis.admin.Dto.Statistics.StatisticsRequestDto;
import com.Tesis.admin.Service.Statistics.IStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class StatisticsController implements IStatisticsController{
    private static final Logger LOG = LoggerFactory.getLogger(StatisticsController.class);


    @Autowired
    private IStatisticsService StatisticsService;


    @Override
    public void addAppUserRoute(StatisticsRequestDto statisticsRequestDto) {
        StatisticsService.add(statisticsRequestDto);
    }

    @Override
    public Optional<StatisticsDto> getById(String id) {
        return StatisticsService.getById(id);
    }

    @Override
    public List<StatisticsDto> getStatistics() {
        return StatisticsService.list();
    }

    @Override
    public List<RouteDetailsDto>  getRoutesByUser(Long appUser) {
        return StatisticsService.getRoutesByUser(appUser);
    }

    @Override
    public List<StatisticsDto> getStatisticsByRoute(String routeId) {
        return StatisticsService.getStatisticsByRoute(routeId);
    }
}
