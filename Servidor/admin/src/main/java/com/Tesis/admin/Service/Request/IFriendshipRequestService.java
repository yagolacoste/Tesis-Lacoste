package com.Tesis.admin.Service.Request;


import com.Tesis.admin.Dto.Request.FriendshipRequestDto;
import com.Tesis.admin.Dto.Request.RequestNotifications;


public interface IFriendshipRequestService {

  void sendRequest(FriendshipRequestDto friendshipRequestDto);

  void acceptedRequest(FriendshipRequestDto friendshipRequestDto);

  void rejectedRequest(FriendshipRequestDto friendshipRequestDto);

  RequestNotifications request(Long userOrigin);
}
