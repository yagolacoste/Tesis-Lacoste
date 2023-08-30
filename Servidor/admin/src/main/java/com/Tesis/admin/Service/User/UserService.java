package com.Tesis.admin.Service.User;


import com.Tesis.admin.Controller.Exception.TypeExceptions.NotFoundException;
import com.Tesis.admin.Dto.AppUser.UserAppDto;
import com.Tesis.admin.Dto.Battle.BattleDto;
import com.Tesis.admin.Dto.Battle.ScoreDto;
import com.Tesis.admin.Dto.Request.RequestDto;
import com.Tesis.admin.Exception.ErrorCodes;
import com.Tesis.admin.Models.StoredDocument;
import com.Tesis.admin.Models.User;
import com.Tesis.admin.Repository.IStoredDocumentRepository;
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

    @Autowired
    private IStoredDocumentRepository iStoredDocumentRepository;


    @Override
    public UserAppDto getByUser(Long id) {
        return userRepository.findById(id).map(UserAppDto::new)
                .orElseThrow(()->new NotFoundException("User by id not found", ErrorCodes.NOT_FOUND.getCode()));
    }

    @Override
    public void add(UserAppDto userAppDto) {
        User user = new User();
        user.setFirstName(userAppDto.getFirstName());
        user.setLastName(userAppDto.getLastName());
        user.setAge(userAppDto.getAge());
        user.setPhone(userAppDto.getPhone());
        user.setEmail(userAppDto.getEmail());
        userRepository.save(user);
    }

    @Override
    public void update(UserAppDto userAppDto, Long id) {
        User user=userRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User by id not found", ErrorCodes.NOT_FOUND.getCode()));
        if(userAppDto.getFirstName()!=null){
            user.setFirstName(userAppDto.getFirstName());
        }
        if(userAppDto.getLastName()!=null){
            user.setLastName(userAppDto.getLastName());
        }
        if(userAppDto.getAge()!=0){
            user.setAge(userAppDto.getAge());
        }
        if(userAppDto.getPhone()!=null){
            user.setPhone(userAppDto.getPhone());
        }
        if(userAppDto.getEmail()!=null){
            user.setEmail(userAppDto.getEmail());
        }
        if(userAppDto.getWeight()!=null){
            user.setWeight(userAppDto.getWeight());
        }
        if(userAppDto.getHeight()!=null){
            user.setHeight(userAppDto.getHeight());
        }
        if(userAppDto.getPhoto()!=null){
            StoredDocument storedDocument=iStoredDocumentRepository.findById(userAppDto.getPhoto()).get();
            user.setStoredDocument(storedDocument);
        }

        userRepository.saveAndFlush(user);
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
    public void addFriend(Long userOrigin, Long userDest) {
        if (!userRepository.existsById(userOrigin)) {
            throw new NotFoundException(ErrorCodes.ACCESS_DENIED.getCode(), "Email not Exists");
        }else
        if (!userRepository.existsById(userDest)) {
            throw new NotFoundException(ErrorCodes.ACCESS_DENIED.getCode(), "Email not Exists");
        }
        User userOrig=userRepository.findById(userOrigin).get();
        User friend=userRepository.findById(userDest).get();
        userOrig.getFriends().add(friend);
        friend.getFriends().add(userOrig);
        userRepository.save(userOrig);
        userRepository.save(friend);

    }

    @Override
    public List<RequestDto> search(Long id, Integer status) {

        return userRepository.getAllNotFriendsAndPending(id, status).stream().map(RequestDto::new).collect(Collectors.toList());
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

    @Override
    public ScoreDto getScore(Long id, String email) {
        ScoreDto scoreDto=new ScoreDto();
        User playerOne= userRepository.findById(id).get();
        scoreDto.setNamePlayerOneComplete(playerOne.getFirstName()+" "+playerOne.getLastName());
        scoreDto.setFileNamePlayerOne(playerOne.getStoredDocument().getCompleteFileName());
        User playerTwo=this.userRepository.findByEmail(email);
        if(playerTwo!=null){
            if(playerOne.getFriends().contains(playerTwo)){
                scoreDto=battleService.getScore(id,playerTwo.getId());
                scoreDto.setFileNamePlayerOne(playerOne.getStoredDocument().getCompleteFileName());
                scoreDto.setFileNamePlayerTwo(playerTwo.getStoredDocument().getCompleteFileName());
                return scoreDto;
            }
            else throw new NotFoundException(playerOne.getFirstName() +"isn't friend ", ErrorCodes.NOT_FOUND.getCode());
        }
        else throw new NotFoundException("User not found", ErrorCodes.NOT_FOUND.getCode());
    }


}
