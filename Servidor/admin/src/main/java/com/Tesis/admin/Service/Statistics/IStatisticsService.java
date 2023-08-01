package com.Tesis.admin.Service.Statistics;


import com.Tesis.admin.Dto.Route.RouteDetailsDto;
import com.Tesis.admin.Dto.Statistics.*;


import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface IStatisticsService {


    void add(StatisticsRequestDto statisticsRequestDto);

    Optional<StatisticsDto> getById(String id);

    List<StatisticsDto> list();

    List<RouteDetailsDto>  getRoutesByUser(Long appUser);

    List<StatisticsDto> getStatisticsByRoute(String routeId);

    AchievementsDto getAchievementsByUser(Long appUser) throws ParseException;

    List<ClassificationDto> getAchievements();

}
