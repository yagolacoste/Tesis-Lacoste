package com.Tesis.admin.Dto.Battle;

import com.Tesis.admin.Models.Battle;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class BattleDto implements Serializable {

    @JsonProperty("idBattle")
    private Long idBattle;


    @JsonProperty("routeId")
    private String routeId;

    @JsonProperty("routeName")
    private String routeName;

    @JsonProperty("completeDate")
    private Date completeDate;

    @JsonProperty("cantParticipant")
    private Integer cantParticipant;

    public BattleDto() {
    }

    public BattleDto(Battle battle) {
        this.idBattle = battle.getIdBattle();
        this.routeId = battle.getRoute().getId();
        this.routeName = battle.getRoute().getName();
        this.completeDate = battle.getCompleteDate();
        this.cantParticipant = battle.getCantParticipant();
    }


    public Long getIdBattle() {
        return idBattle;
    }

    public void setIdBattle(Long idBattle) {
        this.idBattle = idBattle;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
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
}
