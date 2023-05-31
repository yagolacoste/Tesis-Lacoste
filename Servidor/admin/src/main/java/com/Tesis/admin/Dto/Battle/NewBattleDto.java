package com.Tesis.admin.Dto.Battle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import javax.persistence.Convert;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class NewBattleDto implements Serializable {

    @JsonProperty("routeId")
    private String routeId;

    @JsonProperty("completeDate")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date completeDate;

    @JsonProperty("cantParticipant")
    private Integer cantParticipant;

    @JsonProperty("users")
    List<Long> users;

    public NewBattleDto() {
    }


    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
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

    public List<Long> getUsers() {
        return users;
    }

    public void setUsers(List<Long> users) {
        this.users = users;
    }
}
