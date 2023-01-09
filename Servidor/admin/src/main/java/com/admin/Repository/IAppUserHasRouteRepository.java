package com.admin.Repository;

import com.admin.Dto.AppUserHasRoute.StatisticsDTO;
import com.admin.Dto.Route.RouteDetailsDto;
import com.admin.Models.AppUserHasRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAppUserHasRouteRepository extends JpaRepository<AppUserHasRoute, String> {

    @Query("SELECT new com.admin.Dto.Route.RouteDetailsDto(r.id,r.description,r.name,r.coordinates)  FROM AppUserHasRoute ar inner join Route r on (ar.route.id=r.id) where ar.appUser.id = :appUser")
    List<RouteDetailsDto> findByRoute(long appUser);

    @Query("SELECT new com.admin.Dto.AppUserHasRoute.StatisticsDTO(ar) FROM AppUserHasRoute ar where ar.route.id = :routeId")
    List<StatisticsDTO> findAllStatisticsByRoute(String routeId);
}
