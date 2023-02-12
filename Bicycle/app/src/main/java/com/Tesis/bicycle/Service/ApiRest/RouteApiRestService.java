package com.Tesis.bicycle.Service.ApiRest;

import com.Tesis.bicycle.Dto.ApiRest.RouteApiRest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RouteApiRestService {

    @GET("list")
    Call<List<RouteApiRest>> getRoutes();

    @POST("add")
    Call<Void> addRoute(@Body RouteApiRest routeApiRest);


}
