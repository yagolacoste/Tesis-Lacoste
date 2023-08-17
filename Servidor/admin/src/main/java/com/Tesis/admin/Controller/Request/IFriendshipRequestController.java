package com.Tesis.admin.Controller.Request;



import com.Tesis.admin.Dto.Request.FriendshipRequestDto;
import com.Tesis.admin.Dto.Request.RequestDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping(IFriendshipRequestController.PATH)
public interface IFriendshipRequestController {
  public static final String PATH="/friendshiprequest";

  @PostMapping(path = {"/sendrequest"},consumes = MediaType.APPLICATION_JSON_VALUE)
  void sendRequest(@RequestBody FriendshipRequestDto friendshipRequestDto);


  @PutMapping(path = {"/accepted"},consumes = MediaType.APPLICATION_JSON_VALUE)
  void acceptedRequest(@RequestBody FriendshipRequestDto requestDto);

  @PutMapping(path = {"/rejected"},consumes = MediaType.APPLICATION_JSON_VALUE)
  void rejectedRequest(@RequestBody FriendshipRequestDto requestDto);

  @GetMapping(path = {""},produces = MediaType.APPLICATION_JSON_VALUE)
  List<RequestDto> request(@PathVariable("userOrigin") Long userOrigin);
}
