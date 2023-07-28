package com.Tesis.admin.Service.Battle;

import com.Tesis.admin.Controller.Exception.TypeExceptions.NotFoundException;
import com.Tesis.admin.Dto.Battle.BattleDto;
import com.Tesis.admin.Dto.Battle.NewBattleDto;
import com.Tesis.admin.Dto.Battle.ScoreDto;
import com.Tesis.admin.Dto.Statistics.StatisticsDto;
import com.Tesis.admin.Exception.ErrorCodes;
import com.Tesis.admin.Models.*;
import com.Tesis.admin.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BattleService implements IBattleService{

    private static final long DEFAULT=0;

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
        return battleRepository.findAll().stream().map(BattleDto::new).collect(Collectors.toList());
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
        battle.setWinner(DEFAULT);
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
                this.updateWinner(battleDto.getIdBattle());
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



  @Override public BattleDto getRanking(Long battleId) {
      BattleDto battleDto=this.getById(battleId);
      List<StatisticsDto> ranking=this.battleParticipationRepository.getRanking(battleId);
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
    this.updateWinner(battleId);//se actualiza y agrego ganador
  }

    @Override
    public ScoreDto getScore(Long playerOne, Long playerTwo) {
        return battleRepository.getScore(playerOne,playerTwo);
    }



    @Override public int cantBattleByUser(Long appUser) {

        return (int)this.getBattlesByUser(appUser).stream().filter(b->b.getWinner().equals(appUser)).count();
    }



    private void updateWinner(Long battleId){
        Optional<StatisticsDto> statisticsDto =this.getRanking(battleId).getRanking().stream().findFirst();
        if(statisticsDto.isPresent()){
            Long winner=statisticsDto.get().getAppUser();
            Optional<Battle> battle=this.battleRepository.findById(battleId);
            if(battle.isPresent()){
                battle.get().setWinner(winner);
                battleRepository.save(battle.get());
            }
        }
  }
}
