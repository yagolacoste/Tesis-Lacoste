package com.Tesis.bicycle.Service.ApiRest;

import com.Tesis.bicycle.Dto.ApiRest.Battle.BattleDto;
import com.Tesis.bicycle.Dto.ApiRest.Battle.ScoreDto;
import com.Tesis.bicycle.Dto.ApiRest.UserAppDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApiService {

    @GET("get")
    Call<UserAppDto> getById(@Query("id") Long id);


    @GET("{id}/battles")
    Call<List<BattleDto>> getBattleByUser(@Path("id") Long id);

    @GET("{id}/friends")
    Call<List<UserAppDto>> getFriends(@Path("id") Long id);

    @POST("{id}/savefriend")
    Call<Void> saveFriend(@Path("id")Long id,@Query("email")String email);

    @GET("{id}/score")
    Call<ScoreDto> getScore(@Path("id")Long id, @Query("email")String email);

    @PUT("/update")
    Call<Void> updateUser(@Body UserAppDto userAppDto, @Path (value = "id") Long id);

}
