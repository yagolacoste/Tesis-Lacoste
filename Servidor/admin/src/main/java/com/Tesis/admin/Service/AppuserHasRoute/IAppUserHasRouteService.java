package com.Tesis.admin.Service.AppuserHasRoute;


import com.Tesis.admin.Dto.AppUserHasRoute.AppUserHasRouteDetailsDto;
import com.Tesis.admin.Dto.AppUserHasRoute.AppUserHasRouteDto;
import com.Tesis.admin.Dto.AppUserHasRoute.AppUserRouteRequestDto;
import com.Tesis.admin.Dto.AppUserHasRoute.StatisticsDTO;

import java.util.List;
import java.util.Optional;

public interface IAppUserHasRouteService {


    void add(AppUserRouteRequestDto appUserRouteRequestDto);

    Optional<AppUserHasRouteDto> getById(String id);

    List<AppUserHasRouteDto> list();

    AppUserHasRouteDetailsDto getRoutesByUser(Long appUser);

    List<StatisticsDTO> getStatisticsByRoute(String routeId);
}
