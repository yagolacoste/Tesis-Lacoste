package com.Tesis.admin.Service.Battle;

import com.Tesis.admin.Controller.Exception.TypeExceptions.NotFoundException;
import com.Tesis.admin.Dto.Battle.BattleDto;
import com.Tesis.admin.Dto.Battle.NewBattleDto;
import com.Tesis.admin.Dto.Statistics.StatisticsDto;
import com.Tesis.admin.Dto.Statistics.StatisticsRequestDto;
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
import java.util.Date;
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
      List<StatisticsDto> ranking=this.battleParticipationRepository.getRanking(id);
      BattleDto battleDto= battleRepository.findById(id).map(BattleDto::new)
                .orElseThrow(()->new NotFoundException("Battle by id not found",ErrorCodes.NOT_FOUND.getCode()));
      battleDto.setRanking(ranking);
      return battleDto;
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
        List<BattleDto> result = battleParticipationRepository.findByBattlesByUser(id).stream().map(BattleDto::new).collect(Collectors.toList());
        if (!result.isEmpty()) {
            for (BattleDto battleDto : result) {
                List<StatisticsDto> ranking = this.battleParticipationRepository.getRanking(battleDto.getIdBattle());
                battleDto.setRanking(ranking);
                battleDto.setStatus(getStatus(battleDto,id));
            }
            return result;
        }
        return new ArrayList<>();
    }

    private String getStatus(BattleDto battleDto,Long id){
        Date now=new Date();
        int result=now.compareTo(battleDto.getCompleteDate());
        if(result>0 || battleDto.getRanking().size()==battleDto.getCantParticipant()){
            return Status.FINISHED.getStatus();
        }else {
            BattleParticipationId battleParticipationId=new BattleParticipationId(id,battleDto.getIdBattle());
            BattleParticipation battleParticipation=battleParticipationRepository.findById(battleParticipationId).get();
            if(battleParticipation.getStatistics()==null){
                return  Status.NOT_STARTED.getStatus();
            }else
                return Status.PROGRESS.getStatus();
        }
    }



  @Override public BattleDto getRanking(Long id) {
      BattleDto battleDto=this.getById(id);
      List<StatisticsDto> ranking=this.battleParticipationRepository.getRanking(id);
      battleDto.setRanking(ranking);
      return battleDto;
  }



  @Override
  public void updateBattleParticipation(Long appUserId, Long battleId, String statisticsId) {
      BattleParticipationId battleParticipationId=new BattleParticipationId();
      battleParticipationId.setAppuserId(appUserId);
      battleParticipationId.setBattleId(battleId);
      BattleParticipation battleParticipation=battleParticipationRepository.findById(battleParticipationId)
          .orElseThrow(()->new NotFoundException("Battle by id not found",ErrorCodes.NOT_FOUND.getCode()));
      Statistics statistics= StatisticsRepository.getReferenceById(statisticsId);
    battleParticipation.setStatistics(statistics);
    battleParticipationRepository.saveAndFlush(battleParticipation);
  }
}
