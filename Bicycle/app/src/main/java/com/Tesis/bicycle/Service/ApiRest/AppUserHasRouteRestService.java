package com.Tesis.bicycle.Service.ApiRest;

import com.Tesis.bicycle.Dto.ApiRest.AppUserHasRouteDetailsDto;
import com.Tesis.bicycle.Dto.ApiRest.StatisticsDto;
import com.Tesis.bicycle.Dto.ApiRest.AppUserHasRouteApiRest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AppUserHasRouteRestService {

    @POST("add")
    Call<Void> AddStatistics(@Body AppUserHasRouteApiRest appUserHasRouteApiRest);

    @GET("getroutesbyuser")
    Call<AppUserHasRouteDetailsDto> getRouteById(@Query("id") long id);

    @GET("getstatisticsbyroute")
    Call<List<StatisticsDto>> getStatisticsByRoute(@Query("route") String route);


}
