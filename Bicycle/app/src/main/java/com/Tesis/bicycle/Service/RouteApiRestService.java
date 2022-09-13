package com.Tesis.bicycle.Service;

import com.Tesis.bicycle.Model.Route;
import com.Tesis.bicycle.Model.RouteApiRest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface RouteApiRestService {

    @GET("list")
    Call<List<RouteApiRest>> getRoutes();

    @POST("add")
    Call<Void> addRoute(@Body RouteApiRest routeApiRest);
}
