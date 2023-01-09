package com.admin.Controller.User;


import com.admin.Controller.Exception.ErrorCodes;
import com.admin.Controller.Exception.TypeExceptions.NotFoundException;
import com.admin.Dto.UserAppDto;
import com.admin.Models.User;
import com.admin.Service.User.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
    public List<User> getUsers() {
        return userService.list();
    }
}
