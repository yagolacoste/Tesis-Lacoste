package com.Tesis.bicycle.Service.ApiRest;

import com.Tesis.bicycle.Dto.ApiRest.Battle.NewBattleDto;
import com.Tesis.bicycle.Dto.ApiRest.Battle.BattleDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BattleApiService {

    @GET("all")
    Call<List<BattleDto>> getBattles();

    @GET("./")
    Call<BattleDto> getById(@Query("id") Long id);

    @POST("./")
    Call<Void> addBattle(@Body NewBattleDto battleDto);


}
