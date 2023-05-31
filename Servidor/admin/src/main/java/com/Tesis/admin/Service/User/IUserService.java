package com.Tesis.admin.Service.User;

import com.Tesis.admin.Dto.AppUser.UserAppDto;
import com.Tesis.admin.Dto.Battle.BattleDto;
import com.Tesis.admin.Models.User;

import java.util.List;

public interface IUserService {
    UserAppDto getByUser(Long id);

    void add(UserAppDto userAppDto);

    void update(UserAppDto userAppDto, Long id);

    List<UserAppDto> list();

    List<UserAppDto> getFriends(Long id);

    void saveFriend(UserAppDto userAppDto);

    List<BattleDto> getBattlesByUser(Long id);
}
