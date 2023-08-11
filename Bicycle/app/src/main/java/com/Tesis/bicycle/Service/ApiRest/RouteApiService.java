package com.Tesis.bicycle.Service.ApiRest;

import com.Tesis.bicycle.Dto.ApiRest.RouteApiRestDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RouteApiService {

    @GET("list")
    Call<List<RouteApiRestDto>> getRoutes();

    @POST("add")
    Call<Void> addRoute(@Body RouteApiRestDto routeApiRestDto);

    @POST("{id}/addImage")
    Call<Void> addImage( @Path("id") String id,@Query("photo") Long photo);


}
