package com.Tesis.admin.Dto.Battle;

import com.Tesis.admin.Dto.Route.RouteDetailsDto;
import com.Tesis.admin.Dto.Statistics.StatisticsDto;
import com.Tesis.admin.Dto.Statistics.StatisticsRequestDto;
import com.Tesis.admin.Models.Battle;
import com.Tesis.admin.Models.Statistics;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;


public class BattleDto implements Serializable {

    @JsonProperty("idBattle")
    private Long idBattle;


    @JsonProperty("route")
    private RouteDetailsDto route;

    @JsonProperty("completeDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "America/Buenos_Aires")
    private Date completeDate;

    @JsonProperty("cantParticipant")
    private Integer cantParticipant;

    @JsonProperty("ranking")
    List<StatisticsDto> ranking;

    @JsonProperty("status")
    private String status;

    @JsonProperty("winner")
    private Long winner;

    public BattleDto() {
    }

    public BattleDto(Battle battle) {
        this.idBattle = battle.getIdBattle();
        this.route=new RouteDetailsDto(battle.getRoute());
        this.completeDate = battle.getCompleteDate();
        this.cantParticipant = battle.getCantParticipant();
        this.winner=battle.getWinner();
    }


    public Long getIdBattle() {
        return idBattle;
    }

    public void setIdBattle(Long idBattle) {
        this.idBattle = idBattle;
    }

    public RouteDetailsDto getRoute() {
        return route;
    }

    public void setRoute(RouteDetailsDto route) {
        this.route = route;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public Integer getCantParticipant() {
        return cantParticipant;
    }

    public void setCantParticipant(Integer cantParticipant) {
        this.cantParticipant = cantParticipant;
    }



    public List<StatisticsDto> getRanking() {

        return ranking;
    }



    public void setRanking(List<StatisticsDto> ranking) {

        this.ranking = ranking;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public Long getWinner() {

        return winner;
    }



    public void setWinner(Long winner) {

        this.winner = winner;
    }
}
