package com.Tesis.admin.Controller.User;



import com.Tesis.admin.Dto.UserAppDto;
import com.Tesis.admin.Models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(IUserController.PATH)
public interface IUserController {
    static final String PATH ="/user";

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = {"/get"},produces = MediaType.APPLICATION_JSON_VALUE)
    UserAppDto getById(@RequestParam (value = "id") Long id);

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path= {"/add"}, consumes=MediaType.APPLICATION_JSON_VALUE)
    void addUser(@RequestBody @Valid UserAppDto userAppDto);


    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = {"/update"},consumes = MediaType.APPLICATION_JSON_VALUE)
    void updateUser(@RequestBody UserAppDto userAppDto,@RequestParam (value = "id") Long id);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = {"/list"},produces = MediaType.APPLICATION_JSON_VALUE)
    List<User> getUsers();



    
}
