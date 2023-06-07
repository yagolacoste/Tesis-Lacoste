package com.Tesis.admin.Controller.Battle;


import com.Tesis.admin.Dto.Battle.BattleDto;
import com.Tesis.admin.Dto.Battle.NewBattleDto;
import com.Tesis.admin.Service.Battle.IBattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BattleController implements IBattleController{

    @Autowired
    private IBattleService battleService;


    @Override
    public List<BattleDto> getBattles() {
        return battleService.getBattles();
    }

    @Override
    public BattleDto getById(Long id) {
        return battleService.getById(id);
    }

    @Override
    public void addBattle(NewBattleDto battle) {
        battleService.addBattle(battle);
    }



    @Override public BattleDto getRanking(Long id) {

        return battleService.getRanking(id);
    }

}
