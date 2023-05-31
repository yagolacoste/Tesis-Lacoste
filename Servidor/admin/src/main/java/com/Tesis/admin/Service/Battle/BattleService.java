package com.Tesis.admin.Service.Battle;

import com.Tesis.admin.Controller.Exception.TypeExceptions.NotFoundException;
import com.Tesis.admin.Dto.Battle.BattleDto;
import com.Tesis.admin.Dto.Battle.NewBattleDto;
import com.Tesis.admin.Exception.ErrorCodes;
import com.Tesis.admin.Models.*;
import com.Tesis.admin.Repository.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BattleService implements IBattleService{

    @Autowired
    private IBattleRepository battleRepository;

    @Autowired
    private IStatisticsRepository StatisticsRepository;

    @Autowired
    private IRouteRepository routeRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IBattleParticipationRepository battleParticipationRepository;


    @Override
    public List<BattleDto> getBattles() {
        List<BattleDto>result= battleRepository.findAll().stream().map(BattleDto::new).collect(Collectors.toList());
        if(!result.isEmpty()){
            return result;
        }
        return result;
    }

    @Override
    public BattleDto getById(Long id) {

        return battleRepository.findById(id).map(BattleDto::new)
                .orElseThrow(()->new NotFoundException("Battle by id not found",ErrorCodes.NOT_FOUND.getCode()));
    }

    @Override
    public void addBattle(NewBattleDto battleDto) {
       Route route=routeRepository.findById(battleDto.getRouteId())
               .orElseThrow(()->new NotFoundException("Route by id not found",ErrorCodes.NOT_FOUND.getCode()));
        Battle battle=new Battle();
        battle.setCompleteDate(battleDto.getCompleteDate());
        battle.setCantParticipant(battleDto.getCantParticipant());
        battle.setRoute(route);
        battle=battleRepository.save(battle);
        List<BattleParticipation> battleParticipations=new ArrayList<>();
        for(Long idUser:battleDto.getUsers()){
            User user=new User();
            user.setId(idUser);
            BattleParticipation battleParticipation=new BattleParticipation();
            BattleParticipationId battleParticipationId =new BattleParticipationId();
            battleParticipationId.setBattleId(battle.getIdBattle());
            battleParticipationId.setAppuserId(user.getId());
            battleParticipation.setUser(user);
            battleParticipation.setBattle(battle);
            battleParticipation.setId(battleParticipationId);
            battleParticipations.add(battleParticipation);
        }
        battleParticipationRepository.saveAll(battleParticipations);

    }

    @Override
    public List<BattleDto> getBattlesByUser(Long id) {
       List<BattleDto>result=battleParticipationRepository.findByBattlesByUser(id).stream().map(BattleDto::new).collect(Collectors.toList());
       if(!result.isEmpty())
            return  result;
       return new ArrayList<>();
    }
}
