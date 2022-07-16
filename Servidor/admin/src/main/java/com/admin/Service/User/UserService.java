package com.admin.Service.User;

import com.admin.Dto.UserAppDto;
import com.admin.Models.User;
import com.admin.Repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService{

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private  IUserRepository userRepository;


    @Override
    public User getByuser(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public void add(UserAppDto userAppDto) {
        User user = new User();
        user.setUsername(userAppDto.getUserName());
        user.setPassword(userAppDto.getPassword());
        user.setFirstName(userAppDto.getFirstName());
        user.setLastName(userAppDto.getLastName());
        user.setIdentity(userAppDto.getIdentityType());
        user.setIdentityType(userAppDto.getIdentityType());
        user.setAddress(userAppDto.getAddress());
        user.setAge(userAppDto.getAge());
        user.setBirthday(userAppDto.getBirthday());
        user.setPhone(userAppDto.getPhone());
        user.setEmail(userAppDto.getEmail());
        userRepository.save(user);

    }

    @Override
    public void update(UserAppDto userAppDto, Long id) {

    }

    @Override
    public List<User> list() {
        List<User> result=userRepository.findAll();
        return result;
    }
}
