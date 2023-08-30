package com.Tesis.bicycle.Service.ApiRest;

import com.Tesis.bicycle.Dto.ApiRest.Request.FriendshipRequestDto;
import com.Tesis.bicycle.Dto.ApiRest.Request.RequestDto;
import com.Tesis.bicycle.Dto.ApiRest.Request.RequestNotifications;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FriendshipRequestApiService {

    @POST("sendrequest")
    Call<Void> sendRequest(@Body FriendshipRequestDto friendshipRequestDto);


    @PUT("accepted")
    Call<Void> acceptedRequest(@Body FriendshipRequestDto requestDto);

    @PUT("rejected")
    Call<Void> rejectedRequest(@Body FriendshipRequestDto requestDto);

    @GET("./")
    Call<RequestNotifications> request(@Query("userOrigin") Long userOrigin);
}
