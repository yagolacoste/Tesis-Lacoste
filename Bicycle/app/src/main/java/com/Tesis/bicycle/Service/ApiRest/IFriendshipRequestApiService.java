package com.Tesis.bicycle.Service.ApiRest;

import com.Tesis.bicycle.Dto.ApiRest.FriendshipRequest.FriendshipRequestDto;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface IFriendshipRequestApiService {

    @POST("/sendrequest")
    void sendRequest(@Body FriendshipRequestDto friendshipRequestDto);


    @PUT("/accepted")
    void acceptedRequest(@Body FriendshipRequestDto requestDto);

    @PUT("/rejected")
    void rejectedRequest(@Body FriendshipRequestDto requestDto);
}
