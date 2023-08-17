package com.Tesis.admin.Service.Request;


import com.Tesis.admin.Dto.Request.FriendshipRequestDto;
import com.Tesis.admin.Dto.Request.RequestDto;

import java.util.List;


public interface IFriendshipRequestService {

  void sendRequest(FriendshipRequestDto friendshipRequestDto);

  void acceptedRequest(FriendshipRequestDto friendshipRequestDto);

  void rejectedRequest(FriendshipRequestDto friendshipRequestDto);

  List<RequestDto> request(Long userOrigin);
}
