package com.Tesis.admin.Controller.User;



import com.Tesis.admin.Dto.AppUser.UserAppDto;
import com.Tesis.admin.Dto.Battle.BattleDto;
import com.Tesis.admin.Dto.Battle.ScoreDto;
import com.Tesis.admin.Dto.Request.RequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(IUserController.PATH)
public interface IUserController {
    static final String PATH ="/users";

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
    @GetMapping(path = {"/search"},produces = MediaType.APPLICATION_JSON_VALUE)
    List<RequestDto> search(@RequestParam (value = "id") Long id, @RequestParam (value = "status") Integer status);


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path={"{id}/friends"},produces = MediaType.APPLICATION_JSON_VALUE)
    List<UserAppDto> getFriends(@PathVariable (value = "id") Long id);

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path= {"{id}/savefriend"})
    void saveFriend(@RequestParam (value = "email") String email,@PathVariable (value = "id") Long id);


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = {"{id}/battles"},produces = MediaType.APPLICATION_JSON_VALUE)
    List<BattleDto> getBattleByUser(@PathVariable Long id);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = {"{id}/score"},produces = MediaType.APPLICATION_JSON_VALUE)
    ScoreDto getScore(@PathVariable Long id, @RequestParam (value = "name") String email);


}
