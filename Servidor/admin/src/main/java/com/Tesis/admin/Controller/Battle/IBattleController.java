package com.Tesis.admin.Controller.Battle;

import com.Tesis.admin.Dto.Battle.BattleDto;
import com.Tesis.admin.Dto.Battle.NewBattleDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(IBattleController.PATH)
public interface IBattleController {

    public static final String PATH="/battles";

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = {"/all"},produces = MediaType.APPLICATION_JSON_VALUE)
    List<BattleDto> getBattles();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = {""},produces = MediaType.APPLICATION_JSON_VALUE)
    BattleDto getById(@RequestParam(value = "id")Long id);

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = {""},consumes = MediaType.APPLICATION_JSON_VALUE)
    void addBattle(@RequestBody NewBattleDto battle);



}
