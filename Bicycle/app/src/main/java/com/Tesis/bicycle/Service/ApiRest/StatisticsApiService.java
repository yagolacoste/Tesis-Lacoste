package com.Tesis.bicycle.Service.ApiRest;

import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Dto.ApiRest.Statistics.AchievementsDto;
import com.Tesis.bicycle.Dto.ApiRest.Statistics.ClassificationDto;
import com.Tesis.bicycle.Dto.ApiRest.Statistics.StatisticsApiRest;
import com.Tesis.bicycle.Dto.ApiRest.Statistics.StatisticsDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface StatisticsApiService {

    @POST("add")
    Call<Void> AddStatistics(@Body StatisticsApiRest statisticsApiRest);

    @GET("getroutesbyuser")
    Call<List<RouteDetailsDto>> getRouteById(@Query("id") long id);

    @GET("getstatisticsbyroute")
    Call<List<StatisticsDto>> getStatisticsByRoute(@Query("route") String route);

    @GET("achievementbyuser")
    Call<AchievementsDto> getAchievementsByUser(@Query("user") Long appUser);

    @GET("achievements")
    Call<List<ClassificationDto>> getAchievements();


}
