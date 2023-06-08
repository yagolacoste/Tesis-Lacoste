package com.Tesis.bicycle.Dto.ApiRest.Battle;

import com.Tesis.bicycle.Dto.ApiRest.StatisticsDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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

    @SerializedName("ranking")
    //@JsonProperty("ranking")
    List<StatisticsDto> ranking;

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

    public List<StatisticsDto> getRanking() {
        return ranking;
    }

    public void setRanking(List<StatisticsDto> ranking) {
        this.ranking = ranking;
    }
}
