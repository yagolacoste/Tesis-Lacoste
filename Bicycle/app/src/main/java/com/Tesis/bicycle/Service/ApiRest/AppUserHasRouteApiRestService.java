package com.Tesis.bicycle.Service.ApiRest;

import com.Tesis.bicycle.Dto.AppUserHasRouteDetailsDto;
import com.Tesis.bicycle.Model.ApiRest.AppUserHasRouteApiRest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AppUserHasRouteApiRestService {

    @POST("add")
    Call<Void> AddStatistics(@Body AppUserHasRouteApiRest appUserHasRouteApiRest);

    @GET("getroutesbyuser")
    Call<AppUserHasRouteDetailsDto> getRouteById(@Query("id") long id);
}
