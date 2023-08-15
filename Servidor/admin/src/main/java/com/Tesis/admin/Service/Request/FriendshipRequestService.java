package com.Tesis.admin.Service.Request;


import com.Tesis.admin.Dto.Request.FriendshipRequestDto;
import com.Tesis.admin.Exception.ErrorCodes;
import com.Tesis.admin.Exception.NotFoundException;
import com.Tesis.admin.Models.FriendshipRequestStatus;
import com.Tesis.admin.Models.FriendshipRequest;
import com.Tesis.admin.Models.FriendshipRequestId;
import com.Tesis.admin.Models.User;
import com.Tesis.admin.Repository.IFriendshipRequestRepository;
import com.Tesis.admin.Repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FriendshipRequestService implements IFriendshipRequestService {

  @Autowired
  private IFriendshipRequestRepository requestRepository;

  @Autowired
  private IUserRepository userRepository;

  @Override public void sendRequest(FriendshipRequestDto friendshipRequestDto) {
//controlar que pasa si no existen user pero siempre va a ver porque primero lo busco y deberia aparecer
    if(requestRepository.findById(new FriendshipRequestId(friendshipRequestDto)).isEmpty()){
      FriendshipRequest friendshipRequest =new FriendshipRequest();
      FriendshipRequestId friendshipRequestId =new FriendshipRequestId(friendshipRequestDto);
      User userOrigin=userRepository.findById(friendshipRequestDto.getUserOrigin()).get();
      User userDest=userRepository.findById(friendshipRequestDto.getUserDest()).get();
      friendshipRequest.setId(friendshipRequestId);
      friendshipRequest.setUserOrigin(userOrigin);
      friendshipRequest.setUserDest(userDest);
      friendshipRequest.setStatus(FriendshipRequestStatus.PENDING.getValue());
      requestRepository.save(friendshipRequest);
    }else throw new NotFoundException("Existe la solicitud", ErrorCodes.NOT_FOUND.getCode());
  }



  @Override public void acceptedRequest(FriendshipRequestDto friendshipRequestDto) {
      FriendshipRequest friendshipRequest=requestRepository.findById(new FriendshipRequestId(friendshipRequestDto)).get();
      if(friendshipRequest.getStatus().equals(FriendshipRequestStatus.PENDING.getValue())){
        friendshipRequest.setStatus(FriendshipRequestStatus.ACCEPTED.getValue());
        requestRepository.save(friendshipRequest);
      }
  }



  @Override public void rejectedRequest(FriendshipRequestDto friendshipRequestDto) {
    FriendshipRequest friendshipRequest=requestRepository.findById(new FriendshipRequestId(friendshipRequestDto)).get();
    if(friendshipRequest.getStatus().equals(FriendshipRequestStatus.PENDING.getValue())) {
      friendshipRequest.setStatus(FriendshipRequestStatus.REJECTED.getValue());
      requestRepository.save(friendshipRequest);
    }
  }
}
