package com.Tesis.admin.Service.User;


import com.Tesis.admin.Controller.Exception.TypeExceptions.NotFoundException;
import com.Tesis.admin.Dto.AppUser.UserAppDto;
import com.Tesis.admin.Dto.Battle.BattleDto;
import com.Tesis.admin.Exception.ErrorCodes;
import com.Tesis.admin.Models.User;
import com.Tesis.admin.Repository.IUserRepository;
import com.Tesis.admin.Service.Battle.IBattleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService implements IUserService{

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IBattleService battleService;


    @Override
    public UserAppDto getByUser(Long id) {
        UserAppDto user= userRepository.findById(id).map(u->new UserAppDto(u))
                .orElseThrow(()->new NotFoundException("User by id not found", ErrorCodes.NOT_FOUND.getCode()));
       return user;
    }

    @Override
    public void add(UserAppDto userAppDto) {
        User user = new User();
        user.setFirstName(userAppDto.getFirstName());
        user.setLastName(userAppDto.getLastName());

        user.setAge(userAppDto.getAge());

        user.setPhone(userAppDto.getPhone());
        user.setEmail(userAppDto.getEmail());
       // List<User> repeat=this.list();
//        if(!repeat.contains(user))
            userRepository.save(user);
//        else
//            throw new NotFoundException("User is Exist", ErrorCodes.NOT_FOUND.getCode());

    }

    @Override
    public void update(UserAppDto userAppDto, Long id) {

    }

    @Override
    public List<UserAppDto> list() {

        return userRepository.findAll().stream().map(UserAppDto::new).collect(Collectors.toList());
    }

    @Override
    public List<UserAppDto> getFriends(Long id) {
        User user= userRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User by id not found", ErrorCodes.NOT_FOUND.getCode()));

        return user.getFriends().stream().map(UserAppDto::new).collect(Collectors.toList());
    }

    @Override
    public void saveFriend(String email,Long id) {
        if (!userRepository.existsByEmail(email)) {
            throw new NotFoundException(ErrorCodes.ACCESS_DENIED.getCode(), "Email not Exists");
        }
        User friend=userRepository.findByEmail(email);
        User user= userRepository.findById(id)
            .orElseThrow(()->new NotFoundException("User by id not found", ErrorCodes.NOT_FOUND.getCode()));
        if(friend!=null){
                user.getFriends().add(friend);
                friend.getFriends().add(user);
                userRepository.save(user);
                userRepository.save(friend);
            }
    }

    @Override
    public List<BattleDto> getBattlesByUser(Long id) {
        List<BattleDto> result=new ArrayList<>();
        User user= userRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User by id not found", ErrorCodes.NOT_FOUND.getCode()));
        if(user!=null){
            result=battleService.getBattlesByUser(id);
            return result;
        }
        return result;
    }
}
