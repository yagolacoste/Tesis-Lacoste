package com.Tesis.admin.Service.User;

import com.Tesis.admin.Dto.UserAppDto;
import com.Tesis.admin.Models.User;

import java.util.List;

public interface IUserService {
    UserAppDto getByUser(Long id);

    void add(UserAppDto userAppDto);

    void update(UserAppDto userAppDto, Long id);

    List<User> list();
}
