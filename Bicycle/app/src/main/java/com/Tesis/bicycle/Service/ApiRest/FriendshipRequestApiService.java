package com.Tesis.bicycle.Service.ApiRest;

import com.Tesis.bicycle.Dto.ApiRest.FriendshipRequest.FriendshipRequestDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface FriendshipRequestApiService {

    @POST("/sendrequest")
    Call<Void> sendRequest(@Body FriendshipRequestDto friendshipRequestDto);


    @PUT("/accepted")
    Call<Void> acceptedRequest(@Body FriendshipRequestDto requestDto);

    @PUT("/rejected")
    Call<Void> rejectedRequest(@Body FriendshipRequestDto requestDto);
}