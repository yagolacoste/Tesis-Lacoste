package com.admin.Service.AppuserHasRoute;

import com.admin.Dto.AppUserRouteRequestDto;
import com.admin.Models.AppuserHasRoute;
import com.admin.Models.AppuserHasRouteId;

import java.util.List;
import java.util.Optional;

public interface IAppuserHasRouteService {


    void add(AppUserRouteRequestDto appUserRouteRequestDto);

    Optional<AppuserHasRoute> getById(AppuserHasRouteId id);

    List<AppuserHasRoute> list();
}
