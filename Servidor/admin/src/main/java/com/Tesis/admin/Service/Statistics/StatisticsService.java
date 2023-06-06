package com.Tesis.admin.Service.Statistics;



import com.Tesis.admin.Controller.Exception.ErrorCodes;
import com.Tesis.admin.Controller.Exception.TypeExceptions.NotFoundException;
import com.Tesis.admin.Dto.Statistics.StatisticsDetailsDto;
import com.Tesis.admin.Dto.Statistics.StatisticsDto;
import com.Tesis.admin.Dto.Statistics.StatisticsRequestDto;
import com.Tesis.admin.Dto.Route.RouteDetailsDto;
import com.Tesis.admin.Dto.Route.RouteNewRequestDto;
import com.Tesis.admin.Dto.AppUser.UserAppDto;
import com.Tesis.admin.Models.Statistics;
import com.Tesis.admin.Models.Route;
import com.Tesis.admin.Models.User;
import com.Tesis.admin.Repository.IStatisticsRepository;
import com.Tesis.admin.Service.Route.IRouteService;
import com.Tesis.admin.Service.User.IUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StatisticsService implements IStatisticsService {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsService.class);

    @Autowired
    private IStatisticsRepository StatisticsRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRouteService routeService;


    @Override
    public void add(StatisticsRequestDto statisticsRequestDto) {

        //Guarda nueva ruta
        Route r=routeService.getById(statisticsRequestDto.getRoute());
        if(r==null){
            RouteNewRequestDto routeNewRequestDto = new RouteNewRequestDto();
            routeNewRequestDto.setId(statisticsRequestDto.getRoute());
            routeNewRequestDto.setName(statisticsRequestDto.getTitle());
            routeNewRequestDto.setDescription(statisticsRequestDto.getDescription());
            routeNewRequestDto.setCoordinates(statisticsRequestDto.getCoordinates());
            routeNewRequestDto.setDistance(statisticsRequestDto.getDistance());
            routeNewRequestDto.setAvgTime(statisticsRequestDto.getTime());
            routeService.add(routeNewRequestDto);
        }
        //Guarda nuevas estadisticas
        Route route=routeService.getById(statisticsRequestDto.getRoute());
        UserAppDto appUser=userService.getByUser(statisticsRequestDto.getAppUser());
        User u=new User();
        u.setId(appUser.getId());
        Statistics Statistics=new Statistics();
        Statistics.setId(RandomStringUtils.random(10,true,true));
        Statistics.setAppUser(u);///Transformar user
        Statistics.setRoute(route);
        Statistics.setDistance(statisticsRequestDto.getDistance());
        Statistics.setAvgSpeed(statisticsRequestDto.getAvgSpeed());
        Statistics.setTime(statisticsRequestDto.getTime());
        Statistics.setTimeCreated(statisticsRequestDto.getTimeCreated());
        StatisticsRepository.save(Statistics);

    }

    @Override
    public Optional<StatisticsDto> getById(String id) {
        return Optional.ofNullable(StatisticsRepository.findById(id).map(r->new StatisticsDto(r))
                .orElseThrow(() -> new NotFoundException("Not exist user with route", ErrorCodes.NOT_FOUND.getCode())));
    }

    @Override
    public List<StatisticsDto> list() {
        List<StatisticsDto>result= StatisticsRepository.findAll().stream().map(r->new StatisticsDto(r)).collect(Collectors.toList());
        return result;
    }

    @Override
    public StatisticsDetailsDto getRoutesByUser(Long appUser) {
        List<RouteDetailsDto> result=StatisticsRepository.findByRoute(appUser);
        UserAppDto user=userService.getByUser(appUser);
        StatisticsDetailsDto StatisticsDetailsDto= new StatisticsDetailsDto();
        StatisticsDetailsDto.setUserId(appUser);
        StatisticsDetailsDto.setRoutes(result);
        return StatisticsDetailsDto;
    }

    @Override
    public List<StatisticsDto> getStatisticsByRoute(String routeId) {
        return StatisticsRepository.findAllStatisticsByRoute(routeId);
    }
}
