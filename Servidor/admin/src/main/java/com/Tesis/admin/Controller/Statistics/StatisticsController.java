package com.Tesis.admin.Controller.Statistics;


import com.Tesis.admin.Dto.Route.RouteDetailsDto;
import com.Tesis.admin.Dto.Statistics.*;

import com.Tesis.admin.Service.Statistics.IStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
public class StatisticsController implements IStatisticsController{
    private static final Logger LOG = LoggerFactory.getLogger(StatisticsController.class);


    @Autowired
    private IStatisticsService statisticsService;


    @Override
    public void addAppUserRoute(StatisticsRequestDto statisticsRequestDto) {
        statisticsService.add(statisticsRequestDto);
    }

    @Override
    public Optional<StatisticsDto> getById(String id) {
        return statisticsService.getById(id);
    }

    @Override
    public List<StatisticsDto> getStatistics() {
        return statisticsService.list();
    }

    @Override
    public List<RouteDetailsDto>  getRoutesByUser(Long appUser) {
        return statisticsService.getRoutesByUser(appUser);
    }

    @Override
    public List<StatisticsDto> getStatisticsByRoute(String routeId) {
        return statisticsService.getStatisticsByRoute(routeId);
    }

    @Override
    public AchievementsDto getAchievementsByUser(Long appUser) throws ParseException {
        return statisticsService.getAchievementsByUser(appUser);
    }

    @Override
    public List<ClassificationDto> getAchievements() {
        return statisticsService.getAchievements();
    }
}
