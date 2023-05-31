package com.Tesis.admin.Service.Statistics;


import com.Tesis.admin.Dto.Statistics.StatisticsDetailsDto;
import com.Tesis.admin.Dto.Statistics.StatisticsDto;
import com.Tesis.admin.Dto.Statistics.StatisticsRequestDto;


import java.util.List;
import java.util.Optional;

public interface IStatisticsService {


    void add(StatisticsRequestDto statisticsRequestDto);

    Optional<StatisticsDto> getById(String id);

    List<StatisticsDto> list();

    StatisticsDetailsDto getRoutesByUser(Long appUser);

    List<StatisticsDto> getStatisticsByRoute(String routeId);
}
