package com.Tesis.bicycle.Dto.ApiRest.Battle;

import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Dto.ApiRest.Statistics.StatisticsDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
    private List<StatisticsDto> ranking;

    @JsonProperty("status")
    private String status;

    public BattleDto() {
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

    public String getCompleteDateToString(){
        //String inputDateStr = "2023-07-19T03:00:00.000+00:00";

        try {
            SimpleDateFormat isoFormatter = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                isoFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            }
            Date inputDate = isoFormatter.parse(completeDate.toString());

            SimpleDateFormat targetFormatter = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = targetFormatter.format(inputDate);
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
