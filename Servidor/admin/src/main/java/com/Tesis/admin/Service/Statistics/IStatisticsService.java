package com.Tesis.admin.Service.Statistics;


import com.Tesis.admin.Dto.Route.RouteDetailsDto;
import com.Tesis.admin.Dto.Statistics.AchievementsDto;
import com.Tesis.admin.Dto.Statistics.RoutesDto;
import com.Tesis.admin.Dto.Statistics.StatisticsDto;
import com.Tesis.admin.Dto.Statistics.StatisticsRequestDto;


import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface IStatisticsService {


    void add(StatisticsRequestDto statisticsRequestDto);

    Optional<StatisticsDto> getById(String id);

    List<StatisticsDto> list();

    List<RouteDetailsDto>  getRoutesByUser(Long appUser);

    List<StatisticsDto> getStatisticsByRoute(String routeId);

    AchievementsDto getAchievements(Long appUser) throws ParseException;
}
