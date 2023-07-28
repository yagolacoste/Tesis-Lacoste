package com.Tesis.admin.Service.Battle;

import com.Tesis.admin.Dto.Battle.BattleDto;
import com.Tesis.admin.Dto.Battle.NewBattleDto;
import com.Tesis.admin.Dto.Battle.ScoreDto;

import java.util.List;

public interface IBattleService {

    public List<BattleDto> getBattles();

    public BattleDto getById(Long id);

    public void addBattle(NewBattleDto battleDto);

    List<BattleDto> getBattlesByUser(Long id);

    BattleDto getRanking(Long id);

    void updateBattleParticipation(Long appUserId, Long battleId, String statisticsId);


    ScoreDto getScore(Long playerOne, Long playerTwo);

    public int cantBattleByUser(Long appUser);
}
