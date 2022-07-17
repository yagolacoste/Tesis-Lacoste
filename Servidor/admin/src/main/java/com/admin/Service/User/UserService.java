package com.admin.Service.User;

import com.admin.Controller.Exception.ErrorCodes;
import com.admin.Controller.Exception.TypeExceptions.NotFoundException;
import com.admin.Dto.UserAppDto;
import com.admin.Models.User;
import com.admin.Repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService implements IUserService{

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private  IUserRepository userRepository;


    @Override
    public Optional<User> getByuser(Long id) {
       User user= userRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User by id not found", ErrorCodes.NOT_FOUND.getCode()));
       return Optional.ofNullable(user);
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
        List<User> repeat=this.list();
        if(!repeat.contains(user))
            userRepository.save(user);
        else
            throw new NotFoundException("User is Exist", ErrorCodes.NOT_FOUND.getCode());

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
