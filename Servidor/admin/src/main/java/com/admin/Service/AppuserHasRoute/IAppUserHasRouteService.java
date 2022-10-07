package com.admin.Service.AppuserHasRoute;

import com.admin.Dto.AppUserHasRoute.AppUserHasRouteDetailsDto;
import com.admin.Dto.AppUserHasRoute.AppUserRouteRequestDto;
import com.admin.Models.AppUserHasRoute;
import com.admin.Models.AppUserHasRouteId;

import java.util.List;
import java.util.Optional;

public interface IAppUserHasRouteService {


    void add(AppUserRouteRequestDto appUserRouteRequestDto);

    Optional<AppUserHasRoute> getById(AppUserHasRouteId id);

    List<AppUserHasRoute> list();

    AppUserHasRouteDetailsDto getRouteById(Long id);
}
