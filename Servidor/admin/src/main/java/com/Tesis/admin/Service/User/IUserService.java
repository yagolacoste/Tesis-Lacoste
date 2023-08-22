package com.Tesis.admin.Service.User;

import com.Tesis.admin.Dto.AppUser.UserAppDto;
import com.Tesis.admin.Dto.Battle.BattleDto;
import com.Tesis.admin.Dto.Battle.ScoreDto;
import com.Tesis.admin.Dto.Request.RequestDto;

import java.util.List;

public interface IUserService {
    UserAppDto getByUser(Long id);

    void add(UserAppDto userAppDto);

    void update(UserAppDto userAppDto, Long id);

    List<UserAppDto> list();

    List<UserAppDto> getFriends(Long id);

    void saveFriend(String email,Long id);

    List<BattleDto> getBattlesByUser(Long id);

    ScoreDto getScore(Long id, String email);

    void addFriend(Long userOrigin,Long userDest);

    List<RequestDto> search(Long id, Integer status);
}
