package com.Tesis.admin.Controller.Request;



import com.Tesis.admin.Dto.Request.FriendshipRequestDetailsDto;
import com.Tesis.admin.Dto.Request.FriendshipRequestDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping(IFriendshipRequestController.PATH)
public interface IFriendshipRequestController {
  public static final String PATH="/requests";

  @PostMapping(path = {"/sendrequest"},consumes = MediaType.APPLICATION_JSON_VALUE)
  void sendRequest(@RequestBody FriendshipRequestDto friendshipRequestDto);


  @PutMapping(path = {"/accepted"},consumes = MediaType.APPLICATION_JSON_VALUE)
  void acceptedRequest(@RequestBody FriendshipRequestDto requestDto);

  @PutMapping(path = {"/rejected"},consumes = MediaType.APPLICATION_JSON_VALUE)
  void rejectedRequest(@RequestBody FriendshipRequestDto requestDto);

  @GetMapping(path = {"/request"},produces = MediaType.APPLICATION_JSON_VALUE)
  List<FriendshipRequestDetailsDto> acceptedRequest();
}
