package com.Tesis.bicycle.Dto.ApiRest.Battle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDate;
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
