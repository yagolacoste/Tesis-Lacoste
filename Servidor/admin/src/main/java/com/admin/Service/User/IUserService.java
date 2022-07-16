package com.admin.Service.User;

import com.admin.Dto.UserAppDto;
import com.admin.Models.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IUserService {
    User getByuser(Long id);

    void add(UserAppDto userAppDto);

    void update(UserAppDto userAppDto, Long id);

    List<User> list();
}
