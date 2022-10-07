package com.admin.Repository;

import com.admin.Dto.Route.RouteDetailsDto;
import com.admin.Models.AppUserHasRoute;
import com.admin.Models.AppUserHasRouteId;
import com.admin.Models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAppUserHasRouteRepository extends JpaRepository<AppUserHasRoute, AppUserHasRouteId> {


    @Query("SELECT new com.admin.Dto.Route.RouteDetailsDto(r.description,r.weather,r.coordinates)  FROM AppUserHasRoute ar inner join Route r on (ar.id.route=r.id) where ar.id.appUser = :appUser")
    List<RouteDetailsDto> findByRoute(long appUser);
}
