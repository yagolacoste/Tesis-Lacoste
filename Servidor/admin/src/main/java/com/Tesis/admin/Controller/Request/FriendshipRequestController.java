package com.Tesis.admin.Controller.Request;


import com.Tesis.admin.Dto.Request.FriendshipRequestDto;
import com.Tesis.admin.Dto.Request.RequestNotifications;
import com.Tesis.admin.Service.Request.IFriendshipRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FriendshipRequestController implements IFriendshipRequestController {

  @Autowired
  private IFriendshipRequestService requestService;

  @Override public void sendRequest(FriendshipRequestDto friendshipRequestDto) {
      requestService.sendRequest(friendshipRequestDto);
  }



  @Override public void acceptedRequest(FriendshipRequestDto friendshipRequestDto) {
    requestService.acceptedRequest(friendshipRequestDto);
  }



  @Override public void rejectedRequest(FriendshipRequestDto friendshipRequestDto) {
    requestService.rejectedRequest(friendshipRequestDto);
  }

  @Override
  public RequestNotifications request(Long userOrigin) {
    return requestService.request(userOrigin);
  }
}
