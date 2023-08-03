package com.Tesis.bicycle.Dto.ApiRest.Statistics;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;


public class AchievementsDto implements Serializable {

    @JsonProperty("battleWinner")
    private int battleWinner;

    @JsonProperty("speedMaxDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "America/Buenos_Aires")
    private Date speedMaxDate;

    @JsonProperty("speedMax")
    private float speedMax;

    @JsonProperty("distanceMaxDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "America/Buenos_Aires")
    private Date distanceMaxDate;

    @JsonProperty("distanceMax")
    private float distanceMax;

    @JsonProperty("timeMinDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "America/Buenos_Aires")
    private Date timeMinDate;

    @JsonProperty("timeMin")
    private LocalTime timeMin;



    public AchievementsDto() {

    }



    public int getBattleWinner() {

        return battleWinner;
    }



    public void setBattleWinner(int battleWinner) {

        this.battleWinner = battleWinner;
    }



    public String getSpeedMaxDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(speedMaxDate);
    }



    public void setSpeedMaxDate(Date speedMaxDate) {

        this.speedMaxDate = speedMaxDate;
    }



    public float getSpeedMax() {

        return speedMax;
    }



    public void setSpeedMax(float speedMax) {

        this.speedMax = speedMax;
    }



    public String getDistanceMaxDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(distanceMaxDate);
    }



    public void setDistanceMaxDate(Date distanceMaxDate) {

        this.distanceMaxDate = distanceMaxDate;
    }



    public float getDistanceMax() {

        return distanceMax;
    }



    public void setDistanceMax(float distanceMax) {

        this.distanceMax = distanceMax;
    }


    public String getTimeMinDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(timeMinDate);
    }

    public void setTimeMinDate(Date timeMinDate) {
        this.timeMinDate = timeMinDate;
    }

    public LocalTime getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(LocalTime timeMin) {
        this.timeMin = timeMin;
    }


}

