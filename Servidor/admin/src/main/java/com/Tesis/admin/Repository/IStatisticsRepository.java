package com.Tesis.admin.Repository;


import com.Tesis.admin.Dto.Route.RouteDetailsDto;
import com.Tesis.admin.Dto.Statistics.StatisticsDto;
import com.Tesis.admin.Models.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStatisticsRepository extends JpaRepository<Statistics, String> {

    @Query("SELECT distinct new com.Tesis.admin.Dto.Route.RouteDetailsDto(r.id,r.description,r.name,r.coordinates)  FROM Statistics ar inner join Route r on (ar.route.id=r.id) where ar.appUser.id = :appUser")
    List<RouteDetailsDto> findByRoute(long appUser);

    @Query("SELECT new com.Tesis.admin.Dto.Statistics.StatisticsDto(ar) FROM Statistics ar where ar.route.id = :routeId")
    List<StatisticsDto> findAllStatisticsByRoute(String routeId);
}
