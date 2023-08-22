package com.Tesis.admin.Controller.User;



import com.Tesis.admin.Dto.AppUser.UserAppDto;
import com.Tesis.admin.Dto.Battle.BattleDto;
import com.Tesis.admin.Dto.Battle.ScoreDto;
import com.Tesis.admin.Dto.Request.RequestDto;
import com.Tesis.admin.Service.User.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements IUserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;



    @Override
    public UserAppDto getById(Long id) {
        return userService.getByUser(id);

    }

    @Override
    public void addUser(UserAppDto userAppDto) {
        userService.add(userAppDto);
    }

    @Override
    public void updateUser(UserAppDto userAppDto,Long id) {
        userService.update(userAppDto,id);
    }

    @Override
    public List<RequestDto> search(Long id, Integer status) {
        return userService.search(id,status);
    }


    @Override
    public List<UserAppDto> getFriends(Long id) {
        return userService.getFriends(id);
    }

    @Override
    public void saveFriend(String email,Long id) {
        userService.saveFriend(email,id);
    }

    @Override
    public List<BattleDto> getBattleByUser(Long id) {
        return userService.getBattlesByUser(id);
    }

    @Override
    public ScoreDto getScore(Long id, String email) {
        return userService.getScore(id,email);
    }
}
